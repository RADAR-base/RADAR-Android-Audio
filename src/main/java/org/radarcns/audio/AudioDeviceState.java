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
