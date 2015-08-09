# Description #

  * To change the Audio Video Settings it recommended that you use the Test-Application first:
> => see: AudioVideoTestingApplication
  * Don't forget to clear your Browser-Cache if you change the values in the config.xml.


# HowTo #

You find the values in the config.xml

```

<!--
    #############################################################################
    Audio and Video Settings
    Before you change settings here,
    you should first Play and learn how the Settings work in the Documentation:
    http://code.google.com/p/openmeetings/wiki/AudioVideoTestingApplication
    #############################################################################
 -->
    
<!-- Camera Settings for the FramesPerSecond
    
    fps:Number [optional] - The requested rate at which the camera should capture 
    data, in frames per second. The default value is 15.
 -->    
<framesPerSecond>30</framesPerSecond>
    
    
<!-- Camera Settings for the Bandwidth:
    
    bandwidth:Number [optional] - An integer that specifies the maximum amount 
    of bandwidth that the current outgoing video feed can use, in bytes per second. 
    To specify that Flash video can use as much bandwidth as needed to maintain 
    the value of frameQuality, pass 0 for bandwidth. The default value is 16384.
 -->
<!-- These are the settings for the Quality-Modus *normal*
    The Quality-Modus can be chosen in the Login-Box --> 
<bandwidthNeededNormal>16384</bandwidthNeededNormal>    
<!-- These are the settings for the Quality-Modus *best* -->    
<bandwidthNeededBest>32768</bandwidthNeededBest>
    
    
<!-- Camera Settings for the Quality
    
    quality:Number [optional] - An integer that specifies the required level of 
    picture quality, as determined by the amount of compression being applied to 
    each video frame. Acceptable values range from 1 (lowest quality, maximum 
    compression) to 100 (highest quality, no compression). To specify that picture 
    quality can vary as needed to avoid exceeding bandwidth, pass 0 for quality. 
    The default value is 0.
 --> 
<!-- These are the settings for the Quality-Modus *normal* -->  
<camQualityNormal>75</camQualityNormal>     
<!-- These are the settings for the Quality-Modus *best* -->       
<camQualityBest>75</camQualityBest> 
    
    
<!-- Microphone Settings for the Rate:
    
    rate:Number - 
    The rate at which the microphone should capture sound, in kHz. 
    Acceptable values are 5, 8, 11, 22, and 44. 
    The default value is 8 kHz if your sound capture 
    device supports this value. Otherwise, the default value 
    is the next available capture level above 8 kHz that your sound 
    capture device supports, usually 11 kHz.
 -->
<!-- Quality-Modus *normal* -->
<microphoneRateNormal>22</microphoneRateNormal>
<!-- Microphone Settings Quality-Modus *best* -->    
<microphoneRateBest>44</microphoneRateBest>

```