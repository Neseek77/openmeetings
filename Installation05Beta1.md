# Requirements #

  * Postgres installed
  * Open-Office-Service started and listening on port 8100 (see OpenOfficeConverter, this is not necessary for installation but for running later on)
  * Installed imagemagick (this is not necessary for installation but for running later on), you can get more information on http://www.imagemagick.org regarding installation. The instructions for installation can be found there http://www.imagemagick.org/script/binary-releases.php, however on most linux systems you can get it via your favorite package managers (apt-get it)

# Install lated Red5 Server #

  * you can get the latest Version of Red5 from: http://www.osflash.org
  * configure Red5, the default ports Red5 is listening are also the default ports the openmeetings application will try to connect. However you will find the exact port settings in $Red5\_Home/conf/red5.properties (or follow the installation howto's of red5)

# Install OpenLaszlo Server #

  * You can get latest sources of OpenLaszlo 4 from http://www.openlaszlo.org. You can  take current release 4.0.2, follow the installation instructions, install and start it.
  * patch necessary files of OpenLaszlo Component's. Openlaszlo does support VideoStreaming, however there are missing feature's into Video API which prevent it from fully support all feature's. You need to replace to file's:
  * rtmpconnection.lzx: go to $LASZLO\_HOME/Server/lps-4.0.x/lps/components/extensions/av and replace (or rename and copy) it with the rtmpconnection.lzx from here http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/newstreaming/rtmpconnection.lzx
  * same thing for camera.lzx: go to $LASZLO\_HOME/Server/lps-4.0.x/lps/components/extensions/av  and replace (or rename and copy) it with the camera.lzx from http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/newstreaming/camera.lzx

# Import database skeleton #

  * The app uses a database so you have to install the empty skelton of its structure:
  * create a new database **xmlcrm** in your postgres database (default scheme public) (note: you can nuse any name for the database but by default its name is xmlcrm, if you use any other you have to alter the value _connection.url_ in the hibernate.cfg.xml)
> Get the sql from http://openmeetings.googlecode.com/svn/branches/dev/xmlcrm/java/conf/postgres_skeleton.sql and import it into your postgres database (meaning into the database xmlcrm, or whatever name you have chosen) (for example using pgadmin http://www.pgadmin.org/)

# Install OpenMeetings Red5 Webapp #

  * get the Beta1 release from http://code.google.com/p/openmeetings/downloads/list
  * copy it to $Red5\_Home/webapps
  * unrar/pack it there
  * go to ($RED5\_HOME/webapps/)xmlcrm/install/hibernat.cfg.xml and set your database auth into following vars:
```
			<property name="connection.username">postgres</property>
			<property name="connection.password">67810</property>
			<property name="connection.driver_class">org.postgresql.Driver</property>
			<property name="connection.url">jdbc:postgresql://localhost/xmlcrm</property>
```

  * go to $RED5\_HOME and start red5 (wether mit red5.sh or with red5.bat) by default you will have to start red5 as root if you have configured red5 to run on ports lower 1200 on unix systems. However by default it can be started as any user who has read and write access to the red5 directory BUT the openoffice task MUST be started as the same user running red5 (otherwise converted documents will not be readable by the red5-server)
  * copy crossdomain.xml from $RED5\_HOME/webapps/xmlcrm/crossdomain.xml to RED5\_HOME/webapps/root (otherwise you could not upload / download files, however to change the security see http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_14213

# Install OpenMeetings Client #

  * Get the Client from http://code.google.com/p/openmeetings/downloads/list
  * download and unpack it into $LASZLO\_HOME/Server/lps-4.0.x /or/any/subdirectory/of/this/
  * go to $LASZLO\_HOME/Server/lps-4.0.x[/any/subdirectory]/videoconference/config.lzx
> and alter:
```
<attribute name="rmpthostlocal" value="macbook" type="string" /> 
```
to fit your Red5-server-IP. (Port settings by default are red5 defaults, however to change the rtmp or rtmpT port you have to change Red5PortSettings like described in _Install lated Red5 Server_ AND ClientPortSettings)

# Get Started #

  * Install the default value's of the Openmeetings Application
  * go to the Installer by accessing it via browser: http://$rmpthostlocal/xmlcrm/Install it looks like that:

  1. Step 1 - install welcome and reminding
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step1.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step1.png)

  1. Step 2 - adding data
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step2.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step2.png)

  1. Step 3 - installation complete
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step3.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step3.png)

  1. Step 4 - enter Application
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step4.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step4.png)

If you have it that far and openoffice+imagmagick runs correctly your installation is complete