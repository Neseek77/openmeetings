# Description #

  1. You can test all Audio, Video and Streaming Settings used in OpenMeetings. You can test, specify optimize settings. Please think about that this Application uses a video of width 320 and height 240. If you change the width and height, also the Video-Settings might be different to get a superior result.
  1. With this application you can also test how the NetStream/Streaming behaves if the Bandwidth is Bad. For example where and which Events are thrown.
  1. You can test how the Microphone quality changes if you change the rate. (Echo et cetera)
  1. You can change how the client behaves if you re-sync the NetStream (Stop/Resume Broadcasting)
  1. You can check how the client behaves if you do a long-term test (For example after 1 hour Live BroadCasting)

_this application could be extended to test muting, !activityMeter, Simple !VoIP integration and Flix Live/FME2.5 (third Party Publishing Tools)_

  * To change the Default Audio-Video Settings in OpenMeetings see: ChangingAudioVideoSettings

# Where to find #

  * You need to download at least this File: http://openmeetings.googlecode.com/svn/trunk/client/test-setup.lzx
  * no need to download/checkout all sources, this File does not need any additional Library
  * URL (LPS-Verison 4.0.10 used here, of course if you use lps-4.x.xx then you have to replace that!): http://$YOPENLASZLO_HOST:8080/lps-4.0.10/videoconference/test-setup.lzx?lzr=swf8&lzproxied=solo


# HowTo use #

  * Change the URL sothat 127.0.0.1 points to your Red5-Installation!
  * You first need to **connect** to a Red5-host before you start to **broadcast**!
  * You first need **broadcast** a stream with a name before you try to **subscribe** to it!
  * If somebody already uses that streamname you get an NetStream "Bad.StreamName", you can take an StreamName you like, but of course you have to tell the **player** that it will need to use that StreamName!

  * For all Audio-Video related settings I've added the Description to the various values, there are also links in the Sources to the Livedocs of Adobe about the Methods invoked inside the Flash-Player-API (all Actionscript 2)

# Tips and Tricks #

  * If you edit the width/height of the Video it should be always in relation to 640x480 and no Float-Numbers, as it seems like the Flash-Plugin does not accept that and round it to the  next acceptable small number.
  * you should always keep the same logic in using the app:
  1. Connect-Broadcaster
  1. connect-Player
  1. start-streaming-broadcasting
  1. start-subscribing to Live-Stream
  1. stop-subscribing to Live-Stream
  1. stop-Broadcasting
> => and then you can start again at "3. start-streaming-broadcasting", if you mix this up the result will be not equal, for example if you subscribe to the stream while it has not been started, you will see nothing!


# Sample Screen #

![http://openmeetings.googlecode.com/svn/docs/test_application_audio_video.png](http://openmeetings.googlecode.com/svn/docs/test_application_audio_video.png)