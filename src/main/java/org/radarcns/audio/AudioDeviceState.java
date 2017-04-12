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

import android.os.Parcel;
import android.os.Parcelable;

import org.radarcns.android.device.BaseDeviceState;

/**
 * The status on a single point in time
 */
public class AudioDeviceState extends BaseDeviceState {
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AudioDeviceState> CREATOR = new Parcelable.Creator<AudioDeviceState>() {
        public AudioDeviceState createFromParcel(Parcel in) {
            AudioDeviceState result = new AudioDeviceState();
            result.updateFromParcel(in);
            return result;
        }

        public AudioDeviceState[] newArray(int size) {
            return new AudioDeviceState[size];
        }
    };
}
