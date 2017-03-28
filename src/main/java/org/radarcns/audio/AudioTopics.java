package org.radarcns.audio;

import org.radarcns.android.device.DeviceTopics;
import org.radarcns.key.MeasurementKey;
import org.radarcns.topic.AvroTopic;

/** Topic manager for topics concerning the Empatica E4. */
public class AudioTopics extends DeviceTopics {
    private final AvroTopic<MeasurementKey, OpenSmile2PhoneAudio> audioTopic;

    private static final Object syncObject = new Object();
    private static AudioTopics instance = null;

    public static AudioTopics getInstance() {
        synchronized (syncObject) {
            if (instance == null) {
                instance = new AudioTopics();
            }
            return instance;
        }
    }

    private AudioTopics() {
        audioTopic = createTopic("android_processed_audio",
                OpenSmile2PhoneAudio.getClassSchema(),
                OpenSmile2PhoneAudio.class);
    }

    public AvroTopic<MeasurementKey, OpenSmile2PhoneAudio> getAudioTopic() {
        return audioTopic;
    }

}
