# INSTALLATION OF OPENMEETINGS ON RHELS 5.6 x64 (Tikanga) #

We start from a fresh installation with a MySQL database configured and
running.

## 1.INSTALL IMAGEMAGICK ##

```
yum install imagemagick
```

Ghostscript-8.70-6.el5.x86\_64 was already installed in my case

## 2. INSTALL SWFTOOLS ##

It's not in the RHEL repos, so we  have to download it;

```
wget http://www.swftools.org/swftools-0.9.1.tar.gz
```

Before compiling we must install some dependenies;

```
yum install zlib-devel libjpeg-devel.x86_64 freetype-devel giflib-devel
```

We compile swftools as always;

```
tar -xzvf swftools-0.9.1.tar.gz
./configure
make
make install
```

## 3. INSTALL SOX ##

First we must uninstall Sox 12.xx and get the correct one;

```
         yum remove sox
         wget http://sourceforge.net/projects/sox/files/sox/14.3.2/sox-14.3.2.tar.gz/download
         tar -xzvf sox-14.3.2.tar.gz
         ./configure
         make -s
         make install
```

## 4. INSTALL FFMPEG ##

We must remove previous version of Ffmpeg,if we have already installed.
That was my case.

```
yum remove ffmpeg
wget http://www.ffmpeg.org/releases/ffmpeg-snapshot.tar.bz2
bzip2 -d ffmpeg-snapshot.tar.bz2
tar -xvfcd ffffmpeg-snapshot.tar
```

Before compiling due to dependencies, we must add a new repository and
install some packages.

```
rpm -Uhv http://apt.sw.be/redhat/el5/en/x86_64/rpmforge/RPMS/rpmforge-release-0.3.6-1.el5.rf.x86_64.rpm
yum update
yum install yasm
./configure
make
make install
```

## 5. INSTALL JAVA ##
```
yum install java
```

## 6. INSTALL OPENOFFICE ##

You shuld download OO from openoffice.org and choose the correct language.

```
tar -xzvf OOo_3.3.0_Linux_x86-64_install-rpm-wJRE_es.tar.gz
cd OOO330_m20_native_packed-1_es.9567/
./setup
```

After installing OO I create a init script and I move it to the necesary runlevel.


```
  Vi /etc/init.d/openoffice 
```

```
 #!/bin/bash 
        # chkconfig: 345 20 80 
        # description: init.d script for headless openoffice.org (3.3+ 
for RHEL5 64bit) 
        # 
        # processname: soffice 
        # 
        # source function library 
        . /etc/rc.d/init.d/functions 
        RETVAL=0 
        SOFFICE_PATH='/usr/bin/openoffice.org3' 
                   SOFFICE_ARGS='accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager -headless -nofirststartwizard -nologo' 
        SOFFICE_PIDFILE=/var/run/soffice.bin.pid 
        start_soffice() { 
       echo -n $"Starting OpenOffice.org" 
       $SOFFICE_PATH $SOFFICE_ARGS >/dev/null 2>&1 & 
       [ $? -eq 0 ] && echo_success || echo_failure 
       pidof soffice.bin > $SOFFICE_PIDFILE 
       echo 
    } 
    start() { 
       start_soffice 
    } 
    stop() { 
           echo -n $"Stopping OpenOffice" 
           killproc soffice 
           echo 
    } 
    case "$1" in 
           start) 
                   start 
                   ;; 
           stop) 
                   stop 
                   ;; 
           restart) 
                   stop 
                   start 
                   ;; 
           *) 
                   echo $"Usage: $0 {start|stop|restart}" 
    esac 

```

```
/etc/init.d/oppenofice start
```

## 7. END ##

Now we proceed with the standard Openmeetings installation.[Install OpenMeetings](InstallationOpenMeetings#Install_OpenMeetings.md)

I only had one problem during the installation process, I had to set my database default character set to utf8, so I add the following line in /etc/my.cnf

```
                vi /etc/my.cnf 
                default-character-set=utf8 
                /etc/init.d/mysqld restart 

```