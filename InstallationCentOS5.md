# Installation for openmeetings on CentOS 5 #

When installing CentOS 5 set the default installation to Server and select Customise now.  Ensure the following are selected:

`In Development`
```
Development Libraries
Development Tools
```

`In Servers`
```
MySQL Database
```

`In Base System`
```
Java
```

Once installed I generally disable/turn off the following but adjust for your installation:

`In Firewall configuration`
```
Security Level: Disabled
SELinux: Disabled
```

`In System services`
```
apmd
auditd
autofs
avahi-daemon
bluetooth
cpuspeed
cups
firstboot
gpm
hidd
ip6tables
iptables
irqbalance
mcstrans
mdmonitor
microcode_ctl
netfs
nfslock
pcscd
portmap
restorecond
rpcidmapd
rpcsvcgssd
sendmail
smartd
xfs
yum-updatesd
```

Once the OS has been installed run:
```
yum update
```

The following items need to be removed if they have been installed:
```
rpm -e ImageMagick-6.2.8.0-4.el5_1.1
rpm -e --nodeps sox-12.18.1-1
```

Next mysql needs to be configured and started:
```
default-character-set=utf8
character-set-server=utf8
chkconfig mysqld on
service mysqld start
```

Install the rpmforge repo:
```
rpm -Uhv http://apt.sw.be/redhat/el5/en/i386/rpmforge/RPMS/rpmforge-release-0.3.6-1.el5.rf.i386.rpm
```


Edit /etc/rc.local:
```
/usr/lib/openoffice.org3/program/soffice "-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -nologo -headless -nofirststartwizard &
```

Next all of the various dependancies need to be installed:
```
yum install freetype freetype-devel fontconfig fontconfig-devel java-1.6.0-openjdk-devel libtiff libtiff-devel libjpeg-devel libjpeg giflib giflib-devel libpaper libpaper-devel xml-commons-apis
```
```
libpng libpng-devel libxml2 libxml2-devel fftw3 fftw3-devel cairo cairo-devel flac flac-devel wavpack wavpack-devel libsndfile libsndfile-devel libmad libmad-devel yasm-devel yasm gcc gcc-c++
```

Install the required OpenOffice products:
```
yum groupinstall 'Office/Productivity'
yum install openoffice.org-headless
```

Remove the CentOS RPM versions of ghostscript and ghostscript-devel:
```
rpm -e --nodeps ghostscript ghostscript-devel
```

Now we need to download the latest versions of certain programs and manually build and install:

`Ghostscript`
```
cd /usr/src
wget http://ghostscript.com/releases/ghostscript-8.71.tar.gz
tar zxvf ghostscript-8.71.tar.gz
cd ghostscript-8.71
./configure --prefix=/usr
mkdir obj
mkdir bin
make all
make install
```

`Lame`
```
cd /usr/src
wget http://downloads.sourceforge.net/project/lame/lame/3.98.4/lame-3.98.4.tar.gz
tar zxvf lame-3.98.4.tar.gz
cd lame-3.98.4
./configure --prefix=/usr
make all
make install
```

`SWFTOOLS`
```
cd /usr/src
wget http://www.swftools.org/swftools-0.9.1.tar.gz
tar zxvf swftools-0.9.1.tar.gz
cd swftools-0.9.1
./configure --prefix=/usr
make all
make install
```

`ImageMagick`
```
cd /usr/src
wget ftp://ftp.imagemagick.org/pub/ImageMagick/ImageMagick-6.6.4-10.tar.gz
tar zxvf ImageMagick-6.6.4-10.tar.gz
cd ImageMagick-6.6.4-10
./configure --prefix=/usr
make all
make install
```

`FFmpeg`
```
cd /usr/src
svn checkout svn://svn.ffmpeg.org/ffmpeg/trunk ffmpeg
cd ffmpeg
./configure --enable-libmp3lame --enable-postproc --enable-gpl --enable-pthreads --enable-avfilter --prefix=/usr
make all
make install
```

`SoX`
```
cd /usr/src
wget http://downloads.sourceforge.net/project/sox/sox/14.3.1/sox-14.3.1.tar.gz
tar zxvf sox-14.3.1.tar.gz
cd sox-14.3.1
./configure --prefix=/usr
make all
make install
```

`openmeetings`
```
cd /usr/src
wget http://i4demo.com/openmeetings/builds/368/openmeetings_r3537.zip
unzip openmeetings_r3537.zip -d om

cd /usr/src/om/webapps/openmeetings/conf
cp mysql_hibernate.cfg.xml hibernate.cfg.xml
cd /usr/src/om
sh ./red5.sh
```

http://<SERVER IP ADDRESS>:5080/openmeetings/install