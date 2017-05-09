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

import android.content.Context;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.util.Base64;

import org.radarcns.android.data.DataCache;
import org.radarcns.android.data.TableDataHandler;
import org.radarcns.android.device.DeviceManager;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.key.MeasurementKey;
import org.radarcns.opensmile.SmileJNI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/** Manages Phone sensors */
public class AudioDeviceManager implements DeviceManager {
    private static final Logger logger = LoggerFactory.getLogger(AudioDeviceManager.class);

    private final TableDataHandler dataHandler;
    private final Context context;

    private final DeviceStatusListener audioService;

    private final DataCache<MeasurementKey, OpenSmile2PhoneAudio> audioTable;

    private final AudioDeviceState deviceStatus;

    private final String deviceName;
    private final File recordingsDir;
    private boolean isRegistered = false;
    private ScheduledFuture<?> audioReadFuture;
    private final ScheduledExecutorService executor;

    private static final long AUDIO_DURATION_S = 60;
    private static final long AUDIO_REC_RATE_S = 86400;
    private static final String AUDIO_CONFIG_FILE = "liveinput_android.conf";
    private final MediaRecorder recorder;

    public AudioDeviceManager(Context contextIn, DeviceStatusListener phoneService, String groupId,
                              String sourceId, TableDataHandler dataHandler, AudioTopics topics)
            throws IOException {
        this.dataHandler = dataHandler;
        this.audioTable = dataHandler.getCache(topics.getAudioTopic());
        this.audioService = phoneService;
        this.context = contextIn;
        this.recorder = new MediaRecorder();

        File serviceDir = new File(context.getExternalCacheDir(), "org.radarcns.audio");
        recordingsDir = new File(serviceDir, "recordings");
        if (recordingsDir.exists()) {
            for (File file : recordingsDir.listFiles()) {
                if (!file.delete()) {
                    logger.warn("Failed to remove old recording {}", file);
                }
            }
        } else if (!recordingsDir.mkdirs()) {
            throw new IOException("Failed to create recording directory");
        }

        this.deviceStatus = new AudioDeviceState();
        this.deviceStatus.getId().setUserId(groupId);
        this.deviceStatus.getId().setSourceId(sourceId);

        this.deviceName = android.os.Build.MODEL;
        updateStatus(DeviceStatusListener.Status.READY);

        // Scheduler TODO: run executor with existing thread pool/factory
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void start(@NonNull final Set<String> acceptableIds) {
        // Audio recording
        setAudioUpdateRate(AUDIO_REC_RATE_S,AUDIO_DURATION_S,AUDIO_CONFIG_FILE);

        isRegistered = true;
        updateStatus(DeviceStatusListener.Status.CONNECTED);
    }

    private void setAudioUpdateRate(final long period, final long duration, final String configFile) {
        SmileJNI.prepareOpenSMILE(context);
        final String conf = this.context.getCacheDir() + "/" + configFile;//"/liveinput_android.conf";
        final MeasurementKey deviceId = deviceStatus.getId();

        synchronized (this) {
            if (audioReadFuture != null) {
                audioReadFuture.cancel(false);
            }
            audioReadFuture = executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    String filename = recordingsDir.getAbsolutePath() + "/" + UUID.randomUUID().toString();
                    File file = new File(filename);
                    logger.info("Setting up audio recording {}", filename);
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    recorder.setAudioSamplingRate(48000);
                    recorder.setAudioEncodingBitRate(256000);
                    recorder.setOutputFile(filename);
                    try {
                        recorder.prepare();
//                        double startTime = System.currentTimeMillis() / 1000d;
                        recorder.start();   // Recording is now started
                        Thread.sleep(duration * 1000L);
                        recorder.stop();
                        recorder.reset();   // You can reuse the object by going back to setAudioSource() step

                        int length = (int)file.length();

                        logger.info("Reading audio recording {}, size {} bytes", file, length);

                        String b64;
                        try (InputStream fin = new FileInputStream(file);
                                DataInputStream input = new DataInputStream(fin)){
                            byte[] data = new byte[length];
                            input.readFully(data);
                            b64 = Base64.encodeToString(data, Base64.DEFAULT);
                        }

                        logger.info("Audio recording has Base64 encoding length {}", b64.length());

                        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                             GZIPOutputStream gzipOut = new GZIPOutputStream(out)) {
                            gzipOut.write(b64.getBytes());
                            gzipOut.flush();
                            logger.info("Audio recording compressed Base64 encoding length {}", out.toByteArray().length);
                        }

//                        double timeReceived = System.currentTimeMillis() / 1000d;
//                        OpenSmile2PhoneAudio value = new OpenSmile2PhoneAudio(startTime, timeReceived, conf, b64);
//                        dataHandler.addMeasurement(audioTable, deviceId, value);
                    } catch (IOException | InterruptedException ex) {
                        logger.error("Failed to retrieve audio", ex);
                    } finally {
                        if (!file.delete()) {
                            logger.warn("Failed to remove old audio recording");
                        }
                    }
                }
            }, 0, period, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean isClosed() {
        return !isRegistered;
    }


    @Override
    public void close() {
        logger.info("Shutting down recordings");
        isRegistered = false;
        recorder.release(); // Now the object cannot be reused
        executor.shutdown();
        updateStatus(DeviceStatusListener.Status.DISCONNECTED);
    }

    @Override
    public String getName() {
        return deviceName;
    }

    @Override
    public AudioDeviceState getState() {
        return deviceStatus;
    }

    private synchronized void updateStatus(DeviceStatusListener.Status status) {
        this.deviceStatus.setStatus(status);
        this.audioService.deviceStatusUpdated(this, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null
                || !getClass().equals(other.getClass())
                || deviceStatus.getId().getSourceId() == null) {
            return false;
        }

        AudioDeviceManager otherDevice = ((AudioDeviceManager) other);
        return deviceStatus.getId().equals((otherDevice.deviceStatus.getId()));
    }

    @Override
    public int hashCode() {
        return deviceStatus.getId().hashCode();
    }
}
