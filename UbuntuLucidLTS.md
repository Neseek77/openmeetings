#Installation of openmeetings on Ubuntu 10.04.

# Introduction #

with many references from
http://openstudio.info/component/content/article/60
http://code.google.com/p/openmeetings/wiki/InstallationDebian
http://wiki.swftools.org/index.php/Installation


# Details #

Edit the sources to include partner so that the sun-jre will install

```
sudo apt-get install sun-java6-bin sun-java6-jdk sun-java6-jre sun-java6-fonts mysql-server imagemagick gs-gpl libt1-5 zip unzip subversion git-core checkinstall yasm texi2html libfaac-dev libfaad-dev libmp3lame-dev libsdl1.2-dev libx11-dev libxfixes-dev libxvidcore-dev zlib1g-dev libogg-dev sox libvorbis-dev libgsm1 libgsm1-dev libfaad2 flvtool2 lame gcc-multilib autoconf automake1.9 libtool ffmpeg automake
```

and now the openoffice bits

```
sudo apt-get install openoffice.org-writer openoffice.org-calc openoffice.org-impress openoffice.org-draw openoffice.org-math openoffice.org-gcj openoffice.org-filter-binfilter openoffice.org-java-common
```


now the prerequisites for swftools http://www.swftools.org need to be installed

Starting with freetype, get the latest from here http://download.savannah.gnu.org/releases/freetype/

```
mkdir freetype
cd freetype
wget http://download.savannah.gnu.org/releases/freetype/freetype-2.4.5.tar.gz
tar -zxvf freetype-2.4.5.tar.gz
cd freetype-2.4.5
./configure
make
sudo make install
```

now jpeglib

```
sudo apt-get install libjpeg-progs libjpeg62 libjpeg62-dev  
```


and some more

```
sudo apt-get install libgif-dev libgif4 
```

now we can progress with swftools

get the latest from here http://www.swftools.org/download.html

```
mkdir swftools
cd swftools
wget http://www.swftools.org/swftools-2011-01-23-1815.tar.gz
tar -zxvf swftools-2011-01-23-1815.tar.gz
cd swftools-2011-01-23-1815
```

because of some missing items from later versions of libjpeg, xpdf needs to be put into the build

get the latest from  [ftp://ftp.foolabs.com/pub/xpdf/](ftp://ftp.foolabs.com/pub/xpdf/)

```
cd ./lib/pdf
wget ftp://ftp.foolabs.com/pub/xpdf/xpdf-3.02.tar.gz
```

now we can get on with the build

```
cd ../..
./configure
make
sudo make install
```

With all pre-reqs now installed, we can get on with openmeetings, get the latest version from  http://code.google.com/p/openmeetings/downloads/list

```
mkdir openmeetings
cd openmeetings
wget http://openmeetings.googlecode.com/files/openmeetings_1_7_0_r3822.zip
sudo mkdir /opt/red5
sudo cp openmeetings_1_7_0_r3822.zip /opt/red5
cd /opt/red5
sudo unzip openmeetings_1_7_0_r3822.zip
sudo rm openmeetings_1_7_0_r3822.zip
```

now make the scripts executable and change the ownership.

```
sudo chmod +x /opt/red5/*.sh
sudo chmod +x /opt/red5/webapps/openmeetings/jod/*.sh
sudo chown -R nobody\: /opt/red5
```

now you need to create a red5 startup script

```
sudo nano /etc/init.d/red5
```

```
#! /bin/sh
#
# red5 red5 initscript
#
# Author: Simon Eisenmann .
#
set -e
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
DESC="Red5 flash streaming server"
NAME=red5
RED5_HOME=/opt/red5
DAEMON=$RED5_HOME/$NAME.sh
PIDFILE=/var/run/$NAME.pid
SCRIPTNAME=/etc/init.d/$NAME
# Gracefully exit if the package has been removed.
test -x $DAEMON || exit 0
# Read config file if it is present.
if [ -r /etc/default/$NAME ]
then
            . /etc/default/$NAME
fi
#
# Function that starts the daemon/service.
#
d_start() {
            start-stop-daemon --start -c nobody --pidfile $PIDFILE --chdir $RED5_HOME --background --make-pidfile --exec $DAEMON
}
#
# Function that stops the daemon/service.
#
d_stop() {
            start-stop-daemon --stop --quiet --pidfile $PIDFILE --name java
            rm -f $PIDFILE
}
case "$1" in
            start)
   echo -n "Starting $DESC: $NAME"
   d_start
   echo "."
            ;;
            stop)
   echo -n "Stopping $DESC: $NAME"
            d_stop
   echo "."
            ;;

            restart|force-reload)
   echo -n "Restarting $DESC: $NAME"
   d_stop
   sleep 1
   d_start
   echo "."
            ;;

            *)
   echo "Usage: $SCRIPTNAME {start|stop|restart|force-reload}" >&2
   exit 1
            ;;

esac
exit 0
exit 0
```

now make it executable and set to autostart

```
sudo chmod +x /etc/init.d/red5
sudo update-rc.d red5 defaults
```

the database needs to be configured

```
echo "CREATE USER openmeetings@localhost;" | mysql -u root -p
echo "CREATE DATABASE openmeetings DEFAULT CHARACTER SET 'utf8';" | mysql -u root -p
echo "GRANT ALL PRIVILEGES ON openmeetings.* TO 'openmeetings'@'localhost' IDENTIFIED BY '<password>' WITH GRANT OPTION;" | mysql -u root -p
echo "FLUSH PRIVILEGES;" | mysql -u root -p
```

where 

&lt;password&gt;

 is the password that you want to use for the openmeetings user

The openmeetings configuration file needs to be updated with the database details

```
sudo nano /opt/red5/webapps/openmeetings/conf/hibernate.cfg.xml
```

Change

```
<property name="connection.username">root</property>
<property name="connection.password"></property>
```

to

```
<property name="connection.username">openmeetings</property>
<property name="connection.password"><password></property>
```

where 

&lt;password&gt;

 is the password that you used when configuring MySQL

Go to a browser and go to http://f.q.d.n:5080/openmeetings/install to complete the install