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

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.radarbase.android.device.BaseDeviceState;
import org.radarbase.android.device.DeviceServiceProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioServiceProvider extends DeviceServiceProvider<BaseDeviceState> {
    @Override
    public Class<AudioService> getServiceClass() {
        return AudioService.class;
    }

    @Override
    public String getDisplayName() {
        return getRadarService().getString(R.string.header_audio_status);
    }

    @NonNull
    @Override
    public List<String> getPermissionsNeeded() {
        return Arrays.asList(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE);
    }

    @NonNull
    @Override
    public List<String> getFeaturesNeeded() {
        return Collections.singletonList(PackageManager.FEATURE_MICROPHONE);
    }

    @NonNull
    @Override
    public String getSourceProducer() {
        return "OpenSmile";
    }

    @NonNull
    @Override
    public String getSourceModel() {
        return "Audio";
    }

    @NonNull
    @Override
    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }
}
