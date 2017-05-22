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

import org.radarcns.android.device.DeviceManager;
import org.radarcns.android.device.DeviceService;
import org.radarcns.android.device.DeviceTopics;
import org.radarcns.android.util.PersistentStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A service that manages the phone sensor manager and a TableDataHandler to send store the data of
 * the phone sensors and send it to a Kafka REST proxy.
 */
public class AudioService extends DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(AudioService.class);
    private static final String SOURCE_ID_KEY = "source.id";
    private AudioTopics topics;
    private String sourceId;

    @Override
    public void onCreate() {
        logger.info("Creating Phone Sensor service {}", this);
        super.onCreate();

        topics = AudioTopics.getInstance();
    }

    @Override
    protected DeviceManager createDeviceManager() {
        try {
            return new AudioDeviceManager(this, this, getUserId(), getSourceId(), getDataHandler(), topics);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to construct audio device manager", ex);
        }
    }

    @Override
    protected AudioDeviceState getDefaultState() {
        return new AudioDeviceState();
    }

    @Override
    protected DeviceTopics getTopics() {
        return topics;
    }

    public String getSourceId() {
        if (sourceId == null) {
            sourceId = new PersistentStorage(AudioService.class).loadOrStoreUUID(SOURCE_ID_KEY);
        }
        return sourceId;
    }
}
