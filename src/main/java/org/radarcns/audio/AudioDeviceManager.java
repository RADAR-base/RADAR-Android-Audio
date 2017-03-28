package org.radarcns.audio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;

import org.apache.commons.compress.utils.IOUtils;
import org.radarcns.android.data.DataCache;
import org.radarcns.android.data.TableDataHandler;
import org.radarcns.android.device.DeviceManager;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.key.MeasurementKey;
import org.radarcns.opensmile.SmileJNI;
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
public class AudioDeviceManager implements DeviceManager {
    private static final Logger logger = LoggerFactory.getLogger(AudioDeviceManager.class);

    private final TableDataHandler dataHandler;
    private final Context context;

    private final DeviceStatusListener audioService;

    private final DataCache<MeasurementKey, OpenSmile2PhoneAudio> audioTable;

    private final AudioDeviceState deviceStatus;

    private final String deviceName;
    private boolean isRegistered = false;
    private ScheduledFuture<?> audioReadFuture;
    private final ScheduledExecutorService executor;

    private static final long AUDIO_DURATION_S = 5;
    private static final long AUDIO_REC_RATE_S = 30;
    private static final String AUDIO_CONFIG_FILE = "liveinput_android.conf";

    public AudioDeviceManager(Context contextIn, DeviceStatusListener phoneService, String groupId, String sourceId, TableDataHandler dataHandler, AudioTopics topics) {
        this.dataHandler = dataHandler;
        this.audioTable = dataHandler.getCache(topics.getAudioTopic());
        this.audioService = phoneService;
        this.context = contextIn;

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

    public final synchronized void setAudioUpdateRate(final long period, final long duration, final String configFile) {
        if (audioReadFuture != null) {
            audioReadFuture.cancel(false);
        }
        SmileJNI.prepareOpenSMILE(context);
        final String conf = this.context.getCacheDir()+"/"+configFile;//"/liveinput_android.conf";
        final MeasurementKey deviceId = deviceStatus.getId();

        audioReadFuture = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                double time = System.currentTimeMillis() / 1_000d;//event.timestamp / 1_000_000_000d;
                double timeReceived = System.currentTimeMillis() / 1_000d;
                final String dataPath = context.getExternalFilesDir("") + "/audio_"+ (new Date()).getTime()+".bin";
                //openSMILE.clas.SMILExtractJNI(conf,1,dataPath);
                SmileJNI smileJNI = new SmileJNI();
                final double finalTime = time;
                final double finalTimeReceived = timeReceived;
                smileJNI.addListener(new SmileJNI.ThreadListener(){
                    @Override
                    public void onFinishedRecording() {
                        try {
                            File dataFile = new File(dataPath);
                            if (dataFile.exists()) {
                                byte[] b = IOUtils.toByteArray(new FileInputStream(dataFile));
                                String b64 = Base64.encodeToString(b, Base64.DEFAULT);
                                OpenSmile2PhoneAudio value = new OpenSmile2PhoneAudio(finalTime, finalTimeReceived, conf, b64);
                                dataHandler.addMeasurement(audioTable, deviceId, value);
                            } else {
                                logger.warn("Failed to read audio file");
                            }
                        } catch (IOException e) {
                            logger.error("Failed to read audio file");
                        }
                    }
                });
                smileJNI.runOpenSMILE(conf,dataPath, duration);
            }
        }, 0, period, TimeUnit.SECONDS);
        logger.info("SMS log: listener activated and set to a period of {}", period);
    }

    @Override
    public boolean isClosed() {
        return !isRegistered;
    }


    @Override
    public void close() {
        isRegistered = false;
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
