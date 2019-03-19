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

import org.radarbase.android.RadarConfiguration;
import org.radarbase.android.device.BaseDeviceState;
import org.radarbase.android.device.DeviceManager;
import org.radarbase.android.device.DeviceService;

/**
 * A service that manages the phone sensor manager and a TableDataHandler to send store the data of
 * the phone sensors and send it to a Kafka REST proxy.
 */

public class AudioService extends DeviceService<BaseDeviceState> {
    public static final String AUDIO_DURATION_S = "audio_duration";
    public static final String AUDIO_RECORD_RATE_S = "audio_record_rate";
    public static final String AUDIO_CONFIG_FILE = "audio_config_file";

    @NonNull
    @Override
    protected AudioDeviceManager createDeviceManager() {
        return new AudioDeviceManager(this);
    }

    @NonNull
    @Override
    protected BaseDeviceState getDefaultState() {
        return new BaseDeviceState();
    }

    @Override
    protected void configureDeviceManager(@NonNull DeviceManager<BaseDeviceState> manager, @NonNull RadarConfiguration configuration) {
        AudioDeviceManager audioManager = (AudioDeviceManager) manager;
        audioManager.setRecordDuration(configuration.getLong(AUDIO_DURATION_S, 15L));
        audioManager.setRecordRate(configuration.getLong(AUDIO_RECORD_RATE_S, 3600L));
        audioManager.setConfigFile(configuration.getString(AUDIO_CONFIG_FILE, "ComParE_2016.conf"));
    }
}
