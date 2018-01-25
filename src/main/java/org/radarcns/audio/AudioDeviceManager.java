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
import org.radarcns.android.device.AbstractDeviceManager;
import org.radarcns.android.device.BaseDeviceState;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.kafka.ObservationKey;
import org.radarcns.opensmile.SmileJNI;
import org.radarcns.topic.AvroTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** Manages Phone sensors */
public class AudioDeviceManager extends AbstractDeviceManager<AudioService, BaseDeviceState> {
    private static final Logger logger = LoggerFactory.getLogger(AudioDeviceManager.class);

    private final AvroTopic<ObservationKey, OpenSmile2PhoneAudio> audioTopic;

    private ScheduledFuture<?> audioReadFuture;
    private final ScheduledExecutorService executor;

    private long AUDIO_DURATION_S;
    private long AUDIO_REC_RATE_S;
    private String AUDIO_CONFIG_FILE;

    public AudioDeviceManager(AudioService service, long AUDIO_DURATION_MS, long AUDIO_RECORD_RATE_MS, String AUDIO_CONFIG_FILE) {
        super(service);
        this.audioTopic = createTopic("android_processed_audio", OpenSmile2PhoneAudio.class);

        this.setAudioDuration(AUDIO_DURATION_MS);
        this.setAudioRecordRate(AUDIO_RECORD_RATE_MS);
        this.setAudioConfigFile(AUDIO_CONFIG_FILE);

        // Scheduler TODO: run executor with existing thread pool/factory
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void start(@NonNull final Set<String> acceptableIds) {
        // Audio recording
        updateStatus(DeviceStatusListener.Status.READY);
        setAudioUpdateRate(AUDIO_REC_RATE_S,AUDIO_DURATION_S,AUDIO_CONFIG_FILE);
        updateStatus(DeviceStatusListener.Status.CONNECTED);
    }

    private void setAudioUpdateRate(final long period, final long duration, final String configFile) {
        SmileJNI.prepareOpenSMILE(getService());
        final String conf = getService().getCacheDir() + "/" + configFile;//"/liveinput_android.conf";

        synchronized (this) {
            if (audioReadFuture != null) {
                audioReadFuture.cancel(false);
            }
            audioReadFuture = executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    updateStatus(DeviceStatusListener.Status.CONNECTED);
                    logger.info("Setting up audio recording");
                    final String dataPath = getService().getExternalFilesDir("") + "/audio_" + new Date().getTime() + ".bin";
                    //openSMILE.clas.SMILExtractJNI(conf,1,dataPath);
                    SmileJNI smileJNI = new SmileJNI();
                    final double startTime = System.currentTimeMillis() / 1_000d; //event.timestamp / 1_000_000_000d;
                    smileJNI.addListener(new SmileJNI.ThreadListener() {
                        @Override
                        public void onFinishedRecording() {
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
                        }
                    });
                    logger.info("Starting audio recording with configuration {} and duration {}, stored to {}", conf, duration, dataPath);
                    smileJNI.runOpenSMILE(conf, dataPath, duration);
                }
            }, 0, period, TimeUnit.SECONDS);
        }
    }

    @Override
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        executor.shutdown();
        super.close();
    }

    public void setAudioDuration(long audioDurationMs) {
        AUDIO_DURATION_S = audioDurationMs;
    }

    public void setAudioRecordRate(long audioRecordRateMs) {
        AUDIO_REC_RATE_S = audioRecordRateMs;
    }

    public void setAudioConfigFile(String audioConfigFile) {
        AUDIO_CONFIG_FILE = audioConfigFile;
    }
}
