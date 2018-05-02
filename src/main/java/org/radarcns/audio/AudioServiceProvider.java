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
import android.support.annotation.NonNull;

import org.radarcns.android.RadarConfiguration;
import org.radarcns.android.device.BaseDeviceState;
import org.radarcns.android.device.DeviceServiceProvider;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioServiceProvider extends DeviceServiceProvider<BaseDeviceState> {
    public static final String AUDIO_DURATION_S = "audio_duration";
    public  static final String AUDIO_RECORD_RATE_S = "audio_record_rate";
    public  static final String AUDIO_CONFIG_FILE = "audio_config_file";

    protected void configure(Bundle bundle) {
        super.configure(bundle);
        RadarConfiguration config = getConfig();
        bundle.putLong(AUDIO_DURATION_S, config.getLong(AUDIO_DURATION_S, 15L));  // default: 5 second
        bundle.putLong(AUDIO_RECORD_RATE_S, config.getLong(AUDIO_RECORD_RATE_S, 3600L));  // default: 180 second
        bundle.putString(AUDIO_CONFIG_FILE, config.getString(AUDIO_CONFIG_FILE, "ComParE_2016.conf"));
    }

    @Override
    public Class<?> getServiceClass() {
        return AudioService.class;
    }

    @Override
    public String getDisplayName() {
        return getRadarService().getString(R.string.header_audio_status);
    }

    @Override
    public List<String> needsPermissions() {
        return Arrays.asList(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE);
    }

    @NonNull
    @Override
    public String getDeviceProducer() {
        return "RADAR";
    }

    @NonNull
    @Override
    public String getDeviceModel() {
        return "Audio";
    }

    @NonNull
    @Override
    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }
}
