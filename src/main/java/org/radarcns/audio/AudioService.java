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

import org.radarcns.android.device.BaseDeviceState;
import org.radarcns.android.device.DeviceService;

/**
 * A service that manages the phone sensor manager and a TableDataHandler to send store the data of
 * the phone sensors and send it to a Kafka REST proxy.
 */

public class AudioService extends DeviceService<BaseDeviceState> {
    private long audioDurationS;
    private long audioRecordRateS;
    private String audioConfigFile;

    @Override
    protected AudioDeviceManager createDeviceManager() {
        return new AudioDeviceManager(this, audioDurationS, audioRecordRateS, audioConfigFile);
    }

    @Override
    protected BaseDeviceState getDefaultState() {
        return new BaseDeviceState();
    }

    @Override
    protected void onInvocation(@NonNull Bundle bundle) {
        super.onInvocation(bundle);

        audioDurationS = bundle.getLong(AudioServiceProvider.AUDIO_DURATION_S);
        audioRecordRateS = bundle.getLong(AudioServiceProvider.AUDIO_RECORD_RATE_S);
        audioConfigFile = bundle.getString(AudioServiceProvider.AUDIO_CONFIG_FILE);

        AudioDeviceManager audioManager = (AudioDeviceManager) getDeviceManager();
        if (audioManager != null) {
            audioManager.setAudioDuration(audioDurationS);
            audioManager.setAudioRecordRate(audioRecordRateS);
            audioManager.setAudioConfigFile(audioConfigFile);
        }
    }
}
