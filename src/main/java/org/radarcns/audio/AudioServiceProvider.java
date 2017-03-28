package org.radarcns.audio;

import android.os.Parcelable;

import org.radarcns.android.device.DeviceServiceProvider;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioServiceProvider extends DeviceServiceProvider<AudioDeviceState> {
    @Override
    public Class<?> getServiceClass() {
        return AudioService.class;
    }

    @Override
    public Parcelable.Creator<AudioDeviceState> getStateCreator() {
        return AudioDeviceState.CREATOR;
    }

    @Override
    public String getDisplayName() {
        return getActivity().getString(R.string.header_audio_status);
    }

    @Override
    public List<String> needsPermissions() {
        return Arrays.asList(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE);
    }
}
