## Default Configuration ##

  * Port 5080: HTTP (For example for file upload and download)
  * Port 1935: RTMP (Flash Stream and Remoting/RPC)
  * Port 8088: RTMP over HTTP-Tunneling (rtmpT)

Additionally red5 uses some ports internally. However those ports are not in use by OpenMeetings by default.

## Configure alternative ports ##

You need to change two files:

  * Server-Side configuration: $RED5\_HOME/conf/red5.properties, you need to restart the red5-service so that changes are online.
  * Client-Side configuration: $RED5\_HOME/webapps/openmeetings/config.xml, you need to clear your browser cache after changing the file.
> > Every server side configuration has a client side representive

| Config-Name | Default-value | Name in red5.properties | Name in config.xml |
|:------------|:--------------|:------------------------|:-------------------|
| HTTP        | 5080          | http.port               | red5httpport       |
| RTMP        | 1935          | rtmp.port               | rtmpport           |
| RTMPT       | 8088          | rtmpt.host              | rtmpTunnelport     |

## Preventing Firewall issues ##

A common way of bypassing the firewall is to change HTTP port to 80 and rtmpT to 443.

A short description about the connection mechanism:
The application first tries connecting to rtmp 3 times, because rtmp is the protocol that is less performance hungry. If that fails the application will fallback to rtmpT.

Most firewalls do allow traffic on port 443 and they normally do not analyse the packets, because 443 is normally SSL encrypted, so the firewall should not be able to read the packets at all.

## OpenMeetings over SSL ##

You can run OpenMeetings completely over SSL. A guide on how to do it will follow.