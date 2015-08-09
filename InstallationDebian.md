# Installing !Openmeetings on debian etch #


---


## First variant (works with lenny as well): ##

Download the following debian package: <a href='http://openmeetings.googlecode.com/files/red5-openmeetings_1.0.2760.noarch.deb'><a href='http://openmeetings.googlecode.com/files/red5-openmeetings_1.0.2760.noarch.deb'>http://openmeetings.googlecode.com/files/red5-openmeetings_1.0.2760.noarch.deb</a></a>

And install it using <b>sudo dpkg -i red5-openmeetings_1.0.2760.noarch.deb</b> command.
All additional utilities, such as Openoffice, Ghostscript, Imagemagick and Swftools should be installed as described below.

## Second variant (outdated): ##

_this sample is rather outdated_

the installation procedure (if you forget of all 3th party requirements) is as simple as the following:

donwload openmeetings-package (you can download it into your home-directory)<br />
unzip it<br />
cd /openmeetings-package/webapps/openmeetings/conf and configure the hibernate.cfg.xml<br />
cd up again to  /openmeetings-package/
run the red5.sh<br />
<br />
that is all!

no copy of any dirs, no /etc/init.d/, ^^
In doubt see what is written down in here:http://code.google.com/p/openmeetings/wiki/InstallationOpenMeetings
There is no mention of any init.d-script or other copy actions.



---


In this how-to I'm going to descript way to install OpenMeetings on debian GNU/Linux Etch (4.0), Steps are following way:

  * What we need!
  * Install MySQL
  * Install xvfb and OpenOffice.org
  * Install ImageMagic & !Ghostscript
  * Install SwfTools
  * Install Java and red5
  * Download openlazslo
  * Install OpenMeetings
  * Installing must new version?
  * Configure OpenMeetings and place files
  * Start every thing
  * !Troobleshooting
  * Author


## What We Need ##

In this article I will use [aptitude](http://en.wikipedia.org/wiki/Aptitude_(program))  to install  packages, if you don't have apptitude please install it before going forward :

```
$su
#apt-get update
#apt-get install aptitude
```


After aptitude we need  [Debian GNU/Linux](http://www.debian.org/), I use version 4.0 (code name Etch), befor installing any thing else I recommend you to create a directory and manage every thing from there:

```
#su
#mkdir /root/tmpOpenMeetings/
#cd  /root/tmpOpenMeetings/
```

## Install !MySQL ##

OpenMeetings can work with wide range of databases, I will install OpenMeetings with [MySQL](http://en.wikipedia.org/wiki/MySQL), to  install !MySQL server:

```
aptitude install mysql-server
```

Add/edit the following lines to /etc/mysql/my.conf to enable localization:

```
[mysqld]
default-character-set=utf8
character-set-server=utf8
```

## Install xvfb and OpenOffice ##

If you would like exchange Presentation and Office documentation in OpenMeetings, you will nedd xvfb and [OpenOffice.org](http://en.wikipedia.org/wiki/OpenOffice.org)

```
#aptitude install xvfb  openoffice.org
```

Then edit Setup.xcu:

```
#vim  /usr/lib/openoffice/share/registry/data/org/openoffice/Setup.xcu
```

You should insert following lines after:  <node oor:name="Office">:

```
    <prop oor:name="ooSetupConnectionURL" oor:type="xs:string">
        <value>socket,host=localhost,port=8100;urp</value>
    </prop>
```


## Install ImageMagick and  Ghostscript ##

[ImageMagick](http://en.wikipedia.org/wiki/ImageMagic)  is a software suite for image manipulation and display, supporting close to 100 image formats. It is mainly used to perform various transformation and conversion operations on images and [Ghostscript](http://ghostscript.com/awki) is a suite of software based on an interpreter for Adobe Systems' PostScript and Portable Document Format (PDF) page description languages.
> To install this tools run:

```
 #aptitude install imagemagick  gs-gpl
```

## Install SWFTools ##

[SWFTools](http://www.swftools.org/) s a collection of SWF manipulation and creation utilities written by Rainer Böhme and Matthias Kramm. It is released under the GPL.

```
#aptitude install swftools
```

If you get this message: "Couldn't find any package whose name or description matched "swftools" " please download swftools directly from web with running:

```
#wget   http://ftp.it.debian.org/debian/pool/main/s/swftools/swftools_0.8.1-1_i386.deb
```

befor installing this ackage should install "libart-2.0-2 " so run:

```
#aptitude install libart-2.0-2
```

and now install SWFTools:

```
#dpkg -i swftools_0.8.1-1_i386.deb
```


## Install Java and Red5 ##
[red5](http://osflash.org/red5)  is a Open Source Flash Server that OpenMeetings based on, so you should install it, and befor installing red5 should install Java Runtime Environment and Java development Kit run:

```
#aptitude install sun-java5-bin
#aptitude install  sun-java5-demo
#
```

If aptitude gives you any error, please download all file with wget and then install

```
#wget http://ftp.tr.debian.org/debian/pool/non-free/s/sun-java5/sun-java5-demo_1.5.0-13-1_i386.deb
#wget http://ftp.de.debian.org/debian/pool/non-free/s/sun-java5/sun-java5-jdk_1.5.0-14-1etch1_i386.deb
# wget http://ftp.de.debian.org/debian/pool/non-free/s/sun-java5/sun-java5-bin_1.5.0-15-1_i386.deb
#dpkg -i sun-java5-demo_1.5.0-13-1_i386.deb sun-java5-jdk_1.5.0-14-1etch1_i386.deb sun-java5-bin_1.5.0-15-1_i386.deb 
```

Now download red5 for Debian GNU/Linux and install it:

```
#wget http://dl.fancycode.com/red5/0.6.3/debian/red5_0.6.3-1_all.deb
#dpkg -i red5_0.6.3-1_all.deb
```

## openlazslo ##

[OpenLaszlo](http://openlaszlo.org/) is an open source platform for the development and delivery of rich Internet applications. to installing it, run:

```
#wget http://download.openlaszlo.org/4.0.6/openlaszlo-4.0.6-unix.tar.gz
#tar -xvf openlaszlo-4.0.6-unix.tar.gz
#mv lps-4.0.6/ /opt/
```


**Note: there is no need to install OpenLaszlo if you only use the application but don't want to modify it.**

**Note: you might would like to have a look at this shell script to start/stop/restart/status, if yes you can use [this how-to](http://wiki.conzul.de/index.php/OpenOffice2.x).**




## Installing OpenMeetings ##

Huraaa, making infrastructure is finished, now we are going to download latest OpenMeeting release and instaling it:


```
#wget http://openmeetings.googlecode.com/files/openmeetings051.zip
#unzip openmeetings051.zip
```

To installing OpenMeetings you should tell OM where is your DataBase server and as I use MySQL as my Server, steps are like this:

```
#cp openmeetings/conf/mysql_hibernate.cfg.xml openmeetings/conf/hibernate.cfg.xml
```

Now edit file and config your database:

```
#vim openmeetings/conf/hibernate.cfg.xml
```

Now change :

```

            <property name="connection.username"><DB_USER></property>
            <property name="connection.password"><DB_PASSWORD></property>
            <property name="connection.url">jdbc:mysql://<DB_HOST>/<DB_NAME></property>
```

For example my config look likes:

```

 <!-- User  / Password -->
                <property name="connection.username">root</property>
                <property name="connection.password">dj76shwrsh16xsvak1</property>

                <!-- Database Settings -->
                <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
                <property name="dialect">org.hibernate.dialect.MySQLInnoDBDialect</property>
                <property name="connection.url">jdbc:mysql://localhost/openmeetings?useUnicode=true&amp;createDatabaseIfNotExist=true&amp;characterEncoding=utf-8</property>

```


## Installing Must new Version? ##

If you would like get must new version, I mean the version is under development, you should get a Snapshot from current source code, to do this follow this steps, if you don't like to have any thing more than OpenMeetings latest version or don't know what's snapshot, skip this step.

  * nstall Subversion if you don't have:

```
#aptitude install subversion
```

Get (checkout) OpenMeetings latest snapshot:

```
#svn checkout http://openmeetings.googlecode.com/svn/branches/dev/
```

I will rename dev to !OpenMeetingsFromSVN and then replace what updated in snapshot (from subversion) with what should be out of date in orginal openmeetings file downloaded from OpenMeetings and OpnLazslo:

```
# mv dev/ OpenMeetingsFromSVN
#cp -r OpenMeetingsFromSVN/laszlo/client/xmlcrm/videoconference/ /opt/lps-4.0.6/Server/lps-4.0.6/ 
```

## Configure OpenMeetings ##
Now, first I update my language files:
```
#cp OpenMeetingsFromSVN/xmlcrm/java/webapp/openmeetings/languages/*  openmeetings/languages/.
```

Ok, It's time to place my OpenMeting in red5 document root to serving:

```
#cp -r openmeetings/ /usr/lib/red5/webapps/
```


## Start every thing ##

Now time to start every thing we installed, I will start OpenOffice.org at first and then OpenLazslo and  will start red5 server:


```
#xvfb-run --server-args='-screen 0 800x600x16' -a /usr/lib/openoffice/program/soffice -headless -nologo -norestore & 
#export JAVA_HOME=/usr/lib/jvm/java-1.5.0-sun
#/opt/lps-4.0.6/Server/tomcat-5.0.24/bin/startup.sh
#/etc/init.d/red5 start
```

Instead of using a virtual frame buffer you may install and use a [/etc/init.d/ooomeetings](http://www.mail-archive.com/openmeetings-dev@googlegroups.com/msg01203/daemons.tar.gz) service.


The last part of instalation is web based, with your web browser (Firefox, Opera, IE, ...) and servers IP address and port 5080 (default):

http://$YOUR_IP:5080/openmeetings/Install

For example:  [http://127.0.0.1:5080/openmeetings/Install](http://127.0.0.1:5080/openmeetings/Install)

after putting information in forms, OpenMeetings will initialize a DataBase (based on information that we gived to him) and now you can access OpenMeetings first page at:

http://$YOUR_IP:8080/lps-4.0.6/videoconference/

For example:  [http://127.0.0.1:5080/openmeetings/](http://127.0.0.1:5080/openmeetings/)


Congratulation, OpenMeetings is ready to use :)



## Touble Shooting ##

If you get any message about locales or fonts, you can use :

```
#dpkg-reconfigure locales
#aptitude install xfonts-base
```

If you would like to change rtmp host, you can use:

```
vim  /opt/lps-4.0.6/Server/lps-4.0.6/videoconference/config.xml 
```




## Author ##

Contributed by Roberto Torresani <erreti@gmail.com>

Edited: Navid Abdi <navid@gnuiran.org>