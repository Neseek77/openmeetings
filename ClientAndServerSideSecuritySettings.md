### Server Side ###

You can protect your OpenMeetings instance from beeing accessed from 3th party by using the file:

$RED5\_HOME/webapps/openmeetings/WEB-INF/red5-web.properties

Content:
```
webapp.virtualHosts=*,localhost, 127.0.0.1
```
Sample file:
http://code.google.com/p/openmeetings/source/browse/trunk/singlewebapp/WebContent/WEB-INF/red5-web.properties

### Client Side ###

And you can restrict the access also for the client side: Every Flash Plugin will check a file called "crossdomain.xml" before it tries to access the server using rtmp/RPC. This file is located:

$RED5\_HOME/webapps/root/crossdomain.xml

Content:
```
<?xml version="1.0"?>
<cross-domain-policy>
        <site-control permitted-cross-domain-policies="all"/>
        <allow-access-from domain="localhost" to-ports="20-65535"/>
        <allow-access-from domain="*.local" to-ports="20-65535"/>
</cross-domain-policy>
```

Sample file:
http://code.google.com/p/openmeetings/source/browse/trunk/singlewebapp/red5-1.0.0-RC1/red5/webapps/root/crossdomain.xml

If you accessing your application behind an Apache Webservre (using mod\_proxy or mod\_jk2) be careful to make the crossdomain.xml accessible.
Flash will always search for this file in the www-root directory of every URL you connect to. You might use [FireBug](https://addons.mozilla.org/de/firefox/addon/firebug/) for example to lookup requests.