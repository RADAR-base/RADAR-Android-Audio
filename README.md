# DEPRECATION NOTICE

All plugin development has moved to https://github.com/RADAR-base/radar-commons-android in the plugins directory. Please view that directory for examples of a plugin.

# Audio RADAR-pRMT

Audio plugin for the RADAR passive remote monitoring app. It uses openSMILE to process audio from
the phone's microphone, extracts features from it, and sends those features. 

# New Default RadarConfiguration Parameters #


| Parameter | Type | Default | Description |
| --------- | ---- | ------- | ----------- |
| `audio_duration` | int (seconds) | 15 | Length in seconds of the audio recording when it started.  |
| `audio_record_rate` | int (seconds) | 3600 | Default interval between two consecutive audio recordings.  |
| `audio_config_file` | string (filepath) | "ComParE_2016.conf" |  Path to openSMILE configuration file. |

Those variables are defined in the following file:

```
src/main/java/org/radarcns/audio/AudioServiceProvider.java:40
```

_Please note_:
The *actual* audio recording duration is stated here:

```
src/main/assets/shared/FrameModeFunctionals.conf.inc
```

However, the ```audio_duration``` parameter from the RadarConfiguration parameters only tells after
which time period a termination signal should be sent to the openSMILE feature extraction thread.
So ```audio_duration``` and frameSize and frameStep in FrameModeFunctionals.conf.inc must all be set
together correctly such that all values correspond to each other.


# Output File #

A CSV file containing ComParE 2016 audio features is saved in the file path as it is stated here:

```
src/main/java/org/radarcns/audio/AudioDeviceManager.java:89
```
