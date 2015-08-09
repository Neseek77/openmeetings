# Requirements #

  * Database availible (MySQL,Postgres,... MSSQL,Oracle,DB2 see [full-list](http://www.hibernate.org/hib_docs/reference/en/html/session-configuration.html#configuration-optional-dialects)) I recommend to make/use an empty database/scheme
  * Open-Office-Service started and listening on port 8100 (see OpenOfficeConverter, this is not necessary for installation but for running later on)
  * Installed `ImageMagick` (this is not necessary for installation but for running later on), you can get more information on http://www.imagemagick.org regarding installation. The instructions for installation can be found there http://www.imagemagick.org/script/binary-releases.php, however on most linux systems you can get it via your favorite package managers (apt-get it)
  * Installed `GhostScript` (this is not necessary for installation but for running later on), you can get more information on http://pages.cs.wisc.edu/~ghost/ regarding installation. The instructions for installation can be found there, however on most linux systems you can get it via your favorite package managers (apt-get it).
  * Installed `SWFTools` (this is not necessary for installation but for running later on), you can get more information on http://www.swftools.org/ regarding installation. Some of the Linux distributions already have it in there package manager see http://packages.debian.org/unstable/utils/swftools)

# Install latest Red5 Server #

  * you can get the latest Version of Red5 from: http://www.osflash.org
  * configure Red5, the default ports Red5 is listening are also the default ports openmeetings application will try to connect. However you will find the exact port settings in _$RED5-HOME/conf/red5.properties_ (or follow the installation of red5)

# Install Openmeetings #
  1. Download Openmeetings 0.5 Rc3 (beta3) from [http://code.google.com/p/openmeetings/downloads/list Openmeetings-Download-Page](.md)
  1. Unpack it to _$RED5-HOME/webapps/_ your new folder structure will be _$RED5-HOME/webapps/xmlcrm/_ this is the base directory of Openmeetings (following $OPENMEETINGS-HOME)
  1. Prepare Database Settings - goto $OPENMEETINGS-HOME/install there are two preconfigured files for MySQL and Postgres: mysql\_hibernate.cfg.xml and postgres\_hibernate.cfg.xml. Following explains it for MySQL-Database-Users, Postgres-Database-Users and Any-Database-Users. Choose Your One!! Of course the database must already exist! Meaning if you want to use  `jdbc:mysql://localhost/xmlcrm` then the database `xmlcrm` must be already an existing empty database!!
    * MySQL-Database-User: Rename `mysql_hibernate.cfg.xml` to `hibernate.cfg.xml` and alter following config values in (`mysql_`)`hibernate.cfg.xml`
```
		<property name="connection.username">root</property>
		<property name="connection.password"></property>

		<property name="connection.url">jdbc:mysql://YOUR_HOSTNAME/YOUR_DATABASE</property>	
```
> > If you go problems in connecting your database you properly need to replace the jdbc-driver with the one for your mysql-version, see http://www.mysql.com/products/connector/j/ for drivers and replace the mysql-connector-java-3.0.6-stable-bin.jar in xmlcrm/WEB-INF/lib with the one you have downloaded. If your mysql server is not located in localhost make sure the mysql-server is listening on that network-interface.
> > Of course you must alter `YOUR_HOSTNAME/YOUR_DATABASE` to fit your needs for example to:`jdbc:mysql://localhost/xmlcrm`
    * Postgres-Database-Users: Rename `postgres_hibernate.cfg.xml` to `hibernate.cfg.xml` and alter following config values in (`postgres_`)`hibernate.cfg.xml`
```
		<!-- User  / Password -->
		<property name="connection.username">postgres</property>
		<property name="connection.password"></property>
		
		<property name="connection.url">jdbc:postgresql://YOUR_HOSTNAME/YOUR_DATABASE</property>	
```
> > If you go problems in connecting your database you properly need to replace the jdbc-driver with the one for your postgres-version, see http://jdbc.postgresql.org/download.html for drivers and replace the postgresql-8.2-504.jdbc2.jar in xmlcrm/WEB-INF/lib with the one you have downloaded. Don't forget that the encoding of the database must be UNICODE! In past Postgres Version this has been Latin1.
> > Of course you must alter `YOUR_HOSTNAME/YOUR_DATABASE` to fit your needs for example to:`jdbc:postgresql://localhost/xmlcrm`
    * Any-Database-Users: Rename `any_hibernate.cfg.xml` to hibernate.cfg.xml.
> > Alter the following config values to fit your needs:
```
		<!-- User  / Password -->
		<property name="connection.username">user</property>
		<property name="connection.password">*****</property>
		
		<!-- Database Settings -->
		<property name="connection.driver_class">org.postgresql.Driver</property>
		<property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
		<property name="connection.url">jdbc:postgresql://localhost/xmlcrm</property>	
		
```
> > You can see a list of available _dialect_'s here [hibernate-SQL-dialects](http://www.hibernate.org/hib_docs/reference/en/html/session-configuration.html#configuration-optional-dialects). You must download the driver for your database and copy it to _$OPENMEETINGS-HOME/WEB-INF/lib_
  1. (Re)start Red5 (The table's and default database scheme will be automatically created by hibernate if there is something wrong with your database values you will see errors in Red5 logfile, you can reduce Error log output by switching _$RED5-HOME/conf/log4j.properties_ from DEBUG to ERROR to see only relevant value's)
  1. go to the Installer by accessing it via browser: http://$rmpthostlocal/xmlcrm/Install it looks like that (If you unpack your stuff on localhost and do not alter the red5 port settings it will be http://localhost:5080/xmlcrm/Install):

  1. Step 1 - install welcome and reminding
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step1.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step1.png)

  1. Step 2 - adding data
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/august_screeninstallationstep2.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/august_screeninstallationstep2.png)

  1. Step 3 - installation complete
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step3a.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step3a.png)

  1. Step 4 - enter Application (http://$rmpthostlocal/xmlcrm/)
![http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step4.png](http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/resources/screens/install_step4.png)

If you have it that far and openoffice+imagmagick runs correctly your installation is complete


# Tips and Tricks #

  * Increase Performace by changing log-Level: Goto _$RED5-HOME/conf/log4j.properties_ and  change the first line to:
```
    log4j.rootCategory=ERROR, CONSOLE 
```
  * Use the Debug-Application: Enter in your browser http://$RED-HOST:$RED5-PORT/xmlcrm/maindebug.lzx.lzr=swf8.swf for example http://localhost:5080/xmlcrm/maindebug.lzx.lzr=swf8.swf
  * If you have Problems with convertion-jops check your Red5-Log output. There will be error Messages which help you. You must install Open-Office and `ImageMagick` and `GhostScript` to run all Convertion-Jobs correctly.