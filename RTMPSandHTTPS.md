_available since OpenMeetings 1.9.x_

There are 3 ways the client communicates with the server:
  * The flash-client uses RTMP protocol to transfer Audio/Video and to send and receive the user data (login et cetera) to the server and back
  * The browser uses HTTP protocol to load the SWF and to upload and download the files (documents, pdfs, images) to the server and back.
  * The screensharing client uses RTMP protocol to transfer screen data and remote control to the server and back

## Prerequisites ##

  1. You need OpenMeetings 1.9.x for this, OpenMeetings 1.8.x does not have those options.
  1. Install OpenMeetings according to the install instructions and check that it runs without problems
  1. Rename the existing keystore file red5/conf/keystore to keystore.bak

## Configuring RTMPS for the Flash Client ##

  1. Create a new keystore and key, use the same password for both:
> (copied from http://trac.red5.org/wiki/Documentation/Tutorials/Red5DeveloperTips/SSLTLS):
```
keytool -keysize 2048 -genkey -alias red5 -keyalg RSA -keystore red5/conf/keystore
Enter keystore password:
Re-enter new password:
What is your first and last name?
[Unknown]:  <your hostname, e.g demo.openmeetings.de>
What is the name of your organizational unit?
[Unknown]:  Dev
What is the name of your organization?
[Unknown]:  OpenMeetings
What is the name of your City or Locality?
[Unknown]:  Henderson
What is the name of your State or Province?
[Unknown]:  Nevada
What is the two-letter country code for this unit?
[Unknown]:  US
Is CN=demo.openmeetings.de, OU=Dev, O=OpenMeetings, L=Henderson, ST=Nevada, C=US correct?
[no]:  yes
Enter key password for <red5>
```

  1. Generate a CSR: keytool -certreq -keyalg RSA -alias red5 -file red5.csr -keystore red5/conf/keystore
  1. Submit CSR to your CA of choice and receive a signed certificate
  1. Import your chosen CA’s root certificate into the keystore (may need to download it from their site – make sure to get the **root** CA and not the intermediate one): keytool -import -alias root -keystore red5/conf/keystore -trustcacerts -file root.crt (note: you may receive a warning that the certificate already exists in the system wide keystore – import anyway)
  1. Import the intermediate certificate(s) you normally receive with the certificate: keytool -import -alias intermed –keystore red5/conf/ keystore -trustcacerts -file intermediate.crt
  1. Import the certificate you received: keytool -import -alias red5 -keystore red5/conf/keystore -trustcacerts -file demo.openmeetings.de.crt

## Set up RTMPS ##

  1. Add the following XML code to red5/conf/red5-core.xml before the final `</beans>` line:

```
  <bean id="rtmpsMinaIoHandler"
    class="org.red5.server.net.rtmps.RTMPSMinaIoHandler">
    <property name="handler" ref="rtmpHandler" />
    <property name="codecFactory" ref="rtmpCodecFactory" />
    <property name="rtmpConnManager" ref="rtmpMinaConnManager" />
<property name="keyStorePassword" value="${rtmps.keystorepass}" />
    <property name="keystoreFile" value="conf/keystore" />
</bean>

<bean id="rtmpsTransport" class="org.red5.server.net.rtmp.RTMPMinaTransport" init-method="start" destroy-method="stop">
    <property name="ioHandler" ref="rtmpsMinaIoHandler" />
    <property name="connectors">
        <list>
            <bean class="java.net.InetSocketAddress">
                <constructor-arg index="0" type="java.lang.String" value="${rtmps.host}" />
                <constructor-arg index="1" type="int" value="${rtmps.port}" />
            </bean>
        </list>
    </property>
    <property name="receiveBufferSize" value="${rtmp.receive_buffer_size}" />
    <property name="sendBufferSize" value="${rtmp.send_buffer_size}" />
    <property name="tcpNoDelay" value="${rtmp.tcp_nodelay}" />
</bean>
```

  1. Edit red5/conf/red5.properties and set “rtmps.port=5443” and “rtmps.keystorepass=password” (password = password you set on your new keystore)
  1. Edit red5/webapps/openmeetings/config.xml and set `<rtmpsslport>5443</rtmpsslport>`, `<useSSL>yes</useSSL>` and `<proxyType>best</proxyType>`
  1. Restart red5 and try to connect – your connection should now be made via RTMPS (close port 1935 to be sure)

## SSL for the web interface ##
If you want to use SSL for the web interface in addition to RTMPS, you need to make some further modifications. This is mainly to secure the server against MITM attacks, additionally some other features like file uploads also use a plain HTTP connection if this is not done. The following instructions assume that you have already set up RTMPS successfully.

  1. Copy this [jee-container-ssl.xml](http://openmeetings.googlecode.com/svn/docs/jee-container-ssl.xml) file to red5/conf/jee-container.xml
  1. Edit red5/webapps/openmeetings/config.xml and set `<protocol>https</protocol>`
  1. Edit red5/conf/red5.properties and set “https.port=443”
  1. Restart red5 and try to connect to https://your.server – you should be redirected to the OpenMeetings app and all access should be via HTTPS or RTMPS (close port 5080 to be sure).

### Credits ###

Credits goto: Nexus and Holger Rabbach for their help and contribution and configuration documention!