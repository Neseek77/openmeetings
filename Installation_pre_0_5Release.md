# Requirements #

  * JDK 1.5
  * JAVA\_HOME set to point to your JDK installation dir (NOT to the JRE)
  * [ooservice](ooservice.md) up und running in headless mode on port 2002
  * Database server (tested with PostgreSQL only)

# Database #

  1. Forward the database skeleton with the init values to the database (database\_init.sql)

# OpenLaszlo #

  1. Install OpenLaszlo 3.4.x
    * Download OpenLaszlo : http://www.openlaszlo.org/download for your system
    * Unzip in your system
  1. You need to replace the default videoComponents with some older/patched ones to make it running. Copy the content of the folder **streaming** from the zip-package to $LPS\_HOME/Server/lps-3.4.x/lps/components/extensions/av/ (Note: Some other OpenLaszlo Apps based on newer version of the video-components might not work with this ones)
  1. copy the folder **videoconference** into your lps-installation (for example /home/myuser/lps-3.4.x/Server/lps-3.4.x/videoconference/ )
  1. alter config.lzx to fit your installation, the variables:
```
   rmpthostlocal should point to the hostname of your red5 server
   rmptport should point to the port of rtmp (default red5-rtmp port is 1935)
   rmptTunnelport should point to the port of rtmpT (default red5-rtmp port is 8088)
   red5httpport should point to the port of http red5 (default red5-rtmp port is 5080)
```
  1. Start OpenLaszlo

# Red5-webapp installation #

  1. Checkout Red5 from svn, build and install it (URL of svn trunk : https://svn1.cvsdude.com/osflash/red5/java )
    * On linux type in the command line : svn co https://svn1.cvsdude.com/osflash/red5/java
    * you need apache ant to build from source, if you are not familiar with this process you have to download one of the precompiled red5-binary releases.
  1. copy the zip **xmlcrm.zip** from the package **pre05.zip** into your Red5 webapp folder. Unzip it. Alter the red5-configuration to fit your port values ($RED5\_HOME/conf/red5.properties) see host.lzx for port values.
  1. Open hibernate.cfg.xml file into xmlcrm/WEB-INF/lib/xmlcrm.jar zip archive.
  1. Edit theses lines into hibernate.cfg.xml to configure login/password for database :
```
			<property name="connection.username">postgres</property>
			<property name="connection.password">*******</property>
			<property name="connection.driver_class">org.postgresql.Driver</property>
			<property name="connection.url">jdbc:postgresql://localhost/openmeetings</property>
```
  1. copy the file **crossdomain.xml** to $RED5/webapps/root/crossdomain.xml (you need this file otherwise you are not able to upload any file to the server)
  1. restart of red5 (run red5.sh/red5.bat) is needed. Browse to $RED%_HOST:5080 (start page should come up)
  1. browse to : $LPS\_HOST:8080/lps-3.4.x/videoconference/main.lzx?lzt=swf8 for test_

