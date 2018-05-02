# Audio RADAR-pRMT

Audio plugin for the RADAR passive remote monitoring app. It uses openSMILE to process audio from
the phone's microphone, extracts features from it, and sends those features. 

# New Default RadarConfiguration Parameters #

- "audio_duration": 15
- "audio_record_rate": 3600
- "audio_config_file": "ComParE_2016.conf"

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
