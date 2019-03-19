/*
 * Copyright 2017 Universität Passau and The Hyve
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.radarcns.audio;

import android.support.annotation.NonNull;
import android.util.Base64;

import org.apache.commons.compress.utils.IOUtils;
import org.radarbase.android.data.DataCache;
import org.radarbase.android.device.AbstractDeviceManager;
import org.radarbase.android.device.BaseDeviceState;
import org.radarbase.android.device.DeviceStatusListener;
import org.radarbase.android.util.SafeHandler;
import org.radarcns.kafka.ObservationKey;
import org.radarcns.opensmile.SmileJNI;
import org.radarcns.passive.opensmile.OpenSmile2PhoneAudio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/** Manages Phone sensors */
public class AudioDeviceManager extends AbstractDeviceManager<AudioService, BaseDeviceState> {
    private static final Logger logger = LoggerFactory.getLogger(AudioDeviceManager.class);

    private final DataCache<ObservationKey, OpenSmile2PhoneAudio> audioTopic;

    private SafeHandler.HandlerFuture audioReadFuture;
    private final SafeHandler executor;

    private long recordDuration;
    private long recordRate;
    private String configFile;

    AudioDeviceManager(AudioService service) {
        super(service);
        this.audioTopic = createCache("android_processed_audio", OpenSmile2PhoneAudio.class);

        executor = new SafeHandler("AndroidAudio", THREAD_PRIORITY_BACKGROUND);
    }

    @Override
    public void start(@NonNull final Set<String> acceptableIds) {
        executor.start();
        // Audio recording
        updateStatus(DeviceStatusListener.Status.READY);
        schedule(recordRate * 1000L, recordDuration, configFile);
        updateStatus(DeviceStatusListener.Status.CONNECTED);
    }

    private void schedule(final long period, final long duration, final String configFile) {
        executor.execute(() -> {
            SmileJNI.prepareOpenSMILE(getService());

            if (audioReadFuture != null) {
                audioReadFuture.cancel();
                audioReadFuture = null;
            }
        });
        executor.delay(period / 4L, () -> {
            final String conf = getService().getCacheDir() + "/" + configFile;//"/liveinput_android.conf";

            audioReadFuture = executor.repeat(period, () -> {
                updateStatus(DeviceStatusListener.Status.CONNECTED);
                logger.info("Setting up audio recording");
                final String dataPath = getService().getExternalFilesDir("") + "/audio_" + new Date().getTime() + ".bin";
                //openSMILE.clas.SMILExtractJNI(conf,1,dataPath);
                SmileJNI smileJNI = new SmileJNI();
                final double startTime = System.currentTimeMillis() / 1_000d; //event.timestamp / 1_000_000_000d;
                smileJNI.addListener(() -> {
                    logger.info("Finished recording audio to file {}", dataPath);
                    try {
                        File dataFile = new File(dataPath);
                        if (dataFile.exists()) {
                            byte[] b = IOUtils.toByteArray(new FileInputStream(dataFile));
                            String b64 = Base64.encodeToString(b, Base64.DEFAULT);
                            double timeReceived = System.currentTimeMillis() / 1_000d;
                            OpenSmile2PhoneAudio value = new OpenSmile2PhoneAudio(startTime, timeReceived, conf, b64);
                            send(audioTopic, value);
                            updateStatus(DeviceStatusListener.Status.READY);
                        } else {
                            logger.warn("Failed to read audio file");
                        }
                    } catch (IOException e) {
                        logger.error("Failed to read audio file");
                    }
                });
                logger.info("Starting audio recording with configuration {} and duration {}, stored to {}", conf, duration, dataPath);
                smileJNI.runOpenSMILE(conf, dataPath, duration);
                return null;
            });
        });
    }

    @Override
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        executor.stop(() -> audioReadFuture = null);
        super.close();
    }

    public void setRecordDuration(long audioDurationMs) {
        recordDuration = audioDurationMs;
    }

    public void setRecordRate(long audioRecordRateMs) {
        recordRate = audioRecordRateMs;
    }

    public void setConfigFile(String audioConfigFile) {
        configFile = audioConfigFile;
    }
}
