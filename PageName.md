If you would like to use Flowplayer to access a recording made you can use the following setup:

```
<a class="rtmp" href="flvRecording_1.flv" >
        <img src="multiple-rtmp-Dateien/play_text_large.png">
</a>
```

and as JavaScript:

```
// install both players with this one call
$f("a.rtmp", "http://releases.flowplayer.org/swf/flowplayer-3.2.7.swf", {

        // configure both players to use rtmp plugin
        clip: {
                provider: 'rtmp'
        },

        // here is our rtpm plugin configuration
        plugins: {
          rtmp: {

                   // use latest RTMP plugin release
                        url: 'flowplayer.rtmp-3.2.3.swf',

                        /*
                                netConnectionUrl defines where the streams are found
                        */
                        netConnectionUrl: 'rtmp://www.openmeetings.de/openmeetings/hibernate'
          }
        }
});
```