**this installation is still valid for release 0.5.2**

# Requirements #

  * Database available (MySQL,Postgres,... MSSQL,Oracle,DB2 see [full-list](http://www.hibernate.org/hib_docs/reference/en/html/configuration-optional.html#configuration-optional-dialects)) It is recommended to make/use an empty database/scheme
> Database MUST listen and allow TCP/IP connections! And the default character-set/scheme must be UTF8! _For example_ a fresh MySQL does **not** listen on TCP/IP by default and has **not** utf8 as default-character-set defined. You have to change these settings and restart your database server so that changes take effect (mysql-config is in my.cnf). Postgres for example does also not listen by default on TCP/IP but uses by default UTF8 (since postgres 7 or 8). To change Postgres listening on TCP/IP check postgresql.conf and add your host to the pg\_hba.conf. In Postgres you need to create a database using **template1** cause template1 create automatically a scheme called **public** which is needed (and since postgres 8 you can use several schemes inside one database public is the default one [Postgres-Docs](http://www.postgresql.org/docs/8.2/interactive/ddl-schemas.html#DDL-SCHEMAS-PATH)).
  * **OpenOffice-Service** started and listening on port 8100 (see OpenOfficeConverter, this is not necessary for installation but for running later on)
  * Installed **ImageMagick** (this is not necessary for installation but for running later on), you can get more information on http://www.imagemagick.org regarding installation. The instructions for installation can be found there http://www.imagemagick.org/script/binary-releases.php, however on most linux systems you can get it via your favorite package managers (apt-get it)
  * Installed **GhostScript** (this is not necessary for installation but for running later on), you can get more information on http://pages.cs.wisc.edu/~ghost/ regarding installation. The instructions for installation can be found there, however on most linux systems you can get it via your favorite package managers (apt-get it).
  * Installed **SWFTools** (this is not necessary for installation but for running later on), you can get more information on http://www.swftools.org/ regarding installation. Some of the Linux distributions already have it in there package manager see http://packages.debian.org/unstable/utils/swftools)
  * MAC OSx / Linux Users => Get Flash Player 10 Beta!! It fixes the CPU Problem for Streaming Video => http://labs.adobe.com/technologies/flashplayer10/
# ScreenCast #

  * Watch the ScreenCast of Basic Installation http://blip.tv/file/836696?filename=Kunthar-OpenmeetingsBasicInstallation696.flv

# Install Red5 Server v0.6.3 #

  * you can get the Version 0.6.3 of Red5 from: http://www.osflash.org
  * configure Red5, the default ports Red5 is listening are also the default ports OpenMeetings application will try to connect. However you will find the exact port settings in _$RED5-HOME/conf/red5.properties_ (or follow the installation of red5)

  * Windows Version of Red5 please use this script for starting Red5: http://openmeetings.googlecode.com/svn/trunk/webapp/webapp/openmeetings/Red5.bat
  * Howto: Download it into your Red5-installation ($RED5\_HOME, next to the red5.jar) and double click on it for starting Red5.

# Install OpenMeetings #
  1. Download OpenMeetings 0.5.1 from [http://code.google.com/p/openmeetings/downloads/list !OpenMeetings-Download-Page](.md) (Please make sure that you use an up-to-date unzip-software)
  1. Unpack it to _$RED5-HOME/webapps/_ your new folder structure will be _$RED5-HOME/webapps/openmeetings/_ this is the base directory of OpenMeetings (following $OPENMEETINGS-HOME)
  1. Prepare Database Settings - go to $OPENMEETINGS-HOME/conf there are two pre-configured files for MySQL and Postgres: mysql\_hibernate.cfg.xml and postgres\_hibernate.cfg.xml. Following explains it for MySQL-Database-Users, Postgres-Database-Users and Any-Database-Users. Choose Your One!! Of course the database must already exist! Meaning if you want to use  `jdbc:mysql://localhost/openmeetings` then the database `openmeetings` must be already an existing empty database (with character encoding utf8)!!
    * MySQL-Database-User: Rename `mysql_hibernate.cfg.xml` to `hibernate.cfg.xml` and alter following config values in (`mysql_`)`hibernate.cfg.xml`
```
		<property name="connection.username">root</property>
		<property name="connection.password"></property>

		<property name="connection.url">jdbc:mysql://YOUR_HOSTNAME/YOUR_DATABASE</property>	
```
> > If you have problems in connecting your database, a common error is that the Database does not listen to TCP/IP Connection, or you properly need to replace the jdbc-driver with the one for your mysql-version, see http://www.mysql.com/products/connector/j/ for drivers and replace the mysql-connector-java-3.1.14-bin.jar in openmeetings/WEB-INF/lib with the one you have downloaded. If your mysql server is not located in localhost make sure the mysql-server is listening on that network-interface.
> > Of course you must alter `YOUR_HOSTNAME/YOUR_DATABASE` to fit your needs for example to:`jdbc:mysql://localhost/openmeetings`
    * Postgres-Database-Users: Rename `postgres_hibernate.cfg.xml` to `hibernate.cfg.xml` and alter following config values in (`postgres_`)`hibernate.cfg.xml`
```
		<!-- User  / Password -->
		<property name="connection.username">postgres</property>
		<property name="connection.password"></property>
		
		<property name="connection.url">jdbc:postgresql://YOUR_HOSTNAME/YOUR_DATABASE</property>	
```
> > If you go problems in connecting your database you properly need to replace the jdbc-driver with the one for your postgres-version, see http://jdbc.postgresql.org/download.html for drivers and replace the postgresql-8.2-504.jdbc2.jar in openmeetings/WEB-INF/lib with the one you have downloaded. Don't forget that the encoding of the database must be UTF8!
> > Of course you must alter `YOUR_HOSTNAME/YOUR_DATABASE` to fit your needs for example to:`jdbc:postgresql://localhost/openmeetings`
    * Any-Database-Users: Rename `any_hibernate.cfg.xml` to hibernate.cfg.xml.
> > Alter the following config values to fit your needs:
```
		<!-- User  / Password -->
		<property name="connection.username">user</property>
		<property name="connection.password">*****</property>
		
		<!-- Database Settings -->
		<property name="connection.driver_class">org.postgresql.Driver</property>
		<property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
		<property name="connection.url">jdbc:postgresql://localhost/openmeetings</property>	
		
```
> > You can see a list of available _dialect_'s here [hibernate-SQL-dialects](http://www.hibernate.org/hib_docs/reference/en/html/session-configuration.html#configuration-optional-dialects). You must download the driver for your database and copy it to _$OPENMEETINGS-HOME/WEB-INF/lib_
  1. (Re)start Red5 (The table's and default database scheme will be automatically created by hibernate if there is something wrong with your database values you will see errors in Red5 logfile, you can reduce Error log output by switching _$RED5-HOME/conf/log4j.properties_ from DEBUG to ERROR to see only relevant value's)
  1. go to the Installer by accessing it via browser: http://$rmpthostlocal/openmeetings/install and follow the instructions

After Running the Installer all basics are installed. Now login and go to the Meeting-Rooms and check all features. You possibly will have some difficulties uploading files. Check if OpenOffice is really running, swftool, ImageMagick (including GhostScript) is available on  your System-Path (or your customize the path in the Configuration).


# Tips and Tricks #

  * French version of Installation Instructions: http://flash.54n.free.fr/?/Root/Debian/OpenMeetings-Installation
  * Use the Debug-Application: Enter in your browser http://$RED-HOST:$RED5-PORT/openmeetings/maindebug.lzx.lzr=swf8.swf for example http://localhost:5080/openmeetings/maindebug.lzx.lzr=swf8.swf
  * If you have Problems with conversion-jobs check your Red5-Log output. There will be error Messages which help you. You must install OpenOffice, ImageMagick, GhostScript and SWFTools to run all conversion-Jobs correctly.
  * if you have problems in converting Files check the Batch-Scripts in $OPENMEETINGS\_HOME/jod
  * If you want to use red5 as a service (without red5.bat), you need to move red5\wrapper\wrapper.exe to red5\wrapper.exe. Then change some path in conf\wrapper.conf (like ..\lib\foo.jar to lib\foo.jar). And finally change the register key in HKEY\_LOCAL\_MACHINE\SYSTEM\CurrentControlSet\Services\Red5\ImagePath to point to the new wrapper.
  * For connecting and running Apache + Red5 both on Port 80 see Nils Eckert's Tutorial about doing it (German) http://wiki.nils-eckert.de/red5/apache-proxy
  * you can query a Mysql Database to get the schema\_collection by using this query:
```
SELECT Table_name, TABLE_COLLATION FROM information_schema.tables WHERE table_schema = 'openmeetings' ORDER BY table_name DESC
```