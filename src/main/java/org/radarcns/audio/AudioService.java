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

import android.os.Bundle;

import org.apache.avro.specific.SpecificRecord;
import org.radarcns.android.RadarConfiguration;
import org.radarcns.android.device.DeviceManager;
import org.radarcns.android.device.DeviceService;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.android.device.DeviceTopics;
import org.radarcns.android.util.PersistentStorage;
import org.radarcns.key.MeasurementKey;
import org.radarcns.topic.AvroTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * A service that manages the phone sensor manager and a TableDataHandler to send store the data of
 * the phone sensors and send it to a Kafka REST proxy.
 */
public class AudioService extends DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(AudioService.class);
    private static final String SOURCE_ID_KEY = "source.id";
    private AudioTopics topics;
    private String groupId;
    private String sourceId;

    @Override
    public void onCreate() {
        logger.info("Creating Phone Sensor service {}", this);
        super.onCreate();

        topics = AudioTopics.getInstance();
    }

    @Override
    protected DeviceManager createDeviceManager() {
        return new AudioDeviceManager(this, this, groupId, getSourceId(), getDataHandler(), topics);
    }

    @Override
    protected AudioDeviceState getDefaultState() {
        AudioDeviceState newStatus = new AudioDeviceState();
        newStatus.setStatus(DeviceStatusListener.Status.CONNECTED);
        return newStatus;
    }

    @Override
    protected DeviceTopics getTopics() {
        return topics;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<AvroTopic<MeasurementKey, ? extends SpecificRecord>> getCachedTopics() {
        return Collections.<AvroTopic<MeasurementKey, ? extends SpecificRecord>>singletonList(
                topics.getAudioTopic()
        );
    }

    @Override
    protected void onInvocation(Bundle bundle) {
        super.onInvocation(bundle);
        if (groupId == null) {
            groupId = RadarConfiguration.getStringExtra(bundle, RadarConfiguration.DEFAULT_GROUP_ID_KEY);
        }
    }

    public String getSourceId() {
        if (sourceId == null) {
            setSourceId(getSourceIdFromFile());
        }
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    private String getSourceIdFromFile() {
        return PersistentStorage.loadOrStoreUUID(AudioService.class, SOURCE_ID_KEY);
    }
}
