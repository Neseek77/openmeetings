# Requirements #

  * JDK 1.5
  * JAVA\_HOME set to point to your JDK installation dir (NOT to the JRE)
  * [ooservice](ooservice.md) up und running in headless mode on port 2002
  * Database server (tested with PostgreSQL only)

# Database #

  1. Forward the database skeleton with the init values to the database (database\_init.sql)

# OpenLaszlo #

  1. Install OpenLaszlo 4.0.x (latest trunk tested with [r4759](https://code.google.com/p/openmeetings/source/detail?r=4759) legals
    * Download OpenLaszlo : http://download.openlaszlo.org/nightly/legals/ for your system
    * Install in your system
  1. You need to replace the default rtmpconnection.lzx with patched ones to make it running. Copy the file **rtmpconnection.lzx** from the here (http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/streaming/rtmpconnection.lzx)  to $LPS\_HOME/Server/lps-4.0.x/lps/components/extensions/av/
  1. copy the folder **videoconference** into your lps-installation (for example /home/myuser/lps-4.0.x/Server/lps-4.0.x/videoconference/ )
  1. alter config.lzx to fit your installation, the variables:
```
   rmpthostlocal should point to the hostname of your red5 server
   rmptport should point to the port of rtmp (default red5-rtmp port is 1935)
   rmptTunnelport should point to the port of rtmpT (default red5-rtmp port is 8088)
   red5httpport should point to the port of http red5 (default red5-rtmp port is 5080)
```
  1. Start OpenLaszlo

# Red5-webapp installation (installation from source) #

  1. Checkout Red5 from svn, build and install it (URL of svn trunk : https://svn1.cvsdude.com/osflash/red5/java )
    * On linux type in the command line : svn co https://svn1.cvsdude.com/osflash/red5/java
    * you need apache ant to build from source, if you are not familiar with this process you have to download one of the precompiled red5-binary releases.
  1. checkout the openmeetings-webapp:
> http://openmeetings.googlecode.com/svn/branches/dev/xmlcrm/java/
  1. edit the file hibernate.cfg in the folder **src/app** of your checkout eclipse project
```
			<property name="connection.username">postgres</property>
			<property name="connection.password">*******</property>
			<property name="connection.driver_class">org.postgresql.Driver</property>
			<property name="connection.url">jdbc:postgresql://localhost/openmeetings</property>
```
  1. compile the content of the folder **src/app** to a compressed jar named xmlcrm.jar into folder **outputjar**
  1. copy the webapp skeleton of (content of the folder webapp of your checkedout project) into your red5-webapp folder
  1. copy all jars from the hibernate-lib folder, your database-driver, xmlcrm.jar, xmlcrm-tamplates.jar + multipartrequest.jar, + commons-lang-2.2.jar, + velocity-1.6.jar
> to your WEB-INF/lib folder
  1. copy the file **crossdomain.xml** to $RED5/webapps/root/crossdomain.xml (you need this file otherwise you are not able to upload any file to the server)
  1. restart of red5 (run red5.sh/red5.bat) is needed. Browse to $RED5\_HOST:5080 (start page should come up)
  1. browse to : $LPS\_HOST:8080/lps-3.4.x/videoconference/main.lzx?lzt=swf8 for test


# Red5-webapp installation (from zip) #

  1. Checkout Red5 from svn, build and install it (URL of svn trunk : https://svn1.cvsdude.com/osflash/red5/java )
    * On linux type in the command line : svn co https://svn1.cvsdude.com/osflash/red5/java
    * you need apache ant to build from source, if you are not familiar with this process you have to download one of the precompiled red5-binary releases.
  1. download zip **xmlcrm\_webapp.zip** (current xmlcrm\_webapp\_r154.zip) from the downloads section into your Red5 webapp folder. Unzip it. Alter the red5-configuration to fit your port values ($RED5\_HOME/conf/red5.properties) see host.lzx for port values.
  1. Open hibernate.cfg.xml file into xmlcrm/WEB-INF/lib/xmlcrm.jar zip archive.
  1. Edit theses lines into hibernate.cfg.xml to configure login/password for database :
```
			<property name="connection.username">postgres</property>
			<property name="connection.password">*******</property>
			<property name="connection.driver_class">org.postgresql.Driver</property>
			<property name="connection.url">jdbc:postgresql://localhost/openmeetings</property>
```
  1. copy the file **crossdomain.xml** to $RED5/webapps/root/crossdomain.xml (you need this file otherwise you are not able to upload any file to the server)
  1. restart of red5 (run red5.sh/red5.bat) is needed. Browse to $RED5\_HOST:5080 (start page should come up)
  1. browse to : $LPS\_HOST:8080/lps-3.4.x/videoconference/main.lzx?lzt=swf8 for test