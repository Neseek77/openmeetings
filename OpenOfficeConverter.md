

# Starting OO in headless with Version OpenOffice 3.0 ++ #

Starting with OO 3.0 you do not need Xvfb anymore you can just use this:

  * for Linux: /path/to/openoffice/program/soffice.bin -headless -nofirststartwizard -accept="socket,host=localhost,port=8100;urp;StarOffice.Service"
  * for Mac OS X: /path/to/openoffice.app/Contents/!MacOS/soffice.bin -headless -nofirststartwizard -accept="socket,host=localhost,port=8100;urp;StarOffice.Service"
  * for Windows: soffice.exe -headless -nofirststartwizard -accept="socket,host=localhost,port=8100;urp;StarOffice.Service"


# Linux (Debian,Ubuntu) #

## Open Office 2.4 Debian, Ubuntu ##
There are some improvements related to Openoffice 2.4 version. If you have to run old versions of Openoffice for any reason, **please find the old section below**. With 2.4, no more xvfb framebuffer needed.

### Installation steps (based on Etch 4.0 i386) ###
  1. Set backports for packages
  * Check your Debian version, find suitable backport source
  * vi(or nano or pico or your very favorite editor) /etc/apt/sources.list
  * add this line
  * deb http://www.backports.org/debian etch-backports main contrib non-free
  * run apt-get update

  1. Install packages
  * Be sure all packages tagged with 2.4.0-4~bpo40+1
  * openoffice.org-headless,
  * openoffice.org-base, openoffice.org-writer, openoffice.org-calc, openoffice.org-impress, openoffice.org-draw, openoffice.org-math, openoffice.org-filter-mobiledev, openoffice.org-filter-binfilter, msttcorefonts, pstoedit, libpaper-utils, ttf-dejavu

  1. Write startup script for test
  * vi ooo.sh

```
unset DISPLAY
/usr/lib/openoffice/program/soffice "-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -nologo -headless -nofirststartwizard
```
  * chmod +x ooo.sh

  1. Run the oOo server as root.
  * sh ooo.sh
  * Check if server started with `netstat -an | grep 8100`
  * Should seem like `tcp 0 0 127.0.0.1:8100    0.0.0.0:*    LISTEN`
  * Check you have soffice process running `ps aux | grep soffice`

  1. Test the conversion from command line
  * cd  /usr/lib/red5/webapps/openmeetings/jod/
  * cp /home/userx/testme.ppt . (figure out the copy ppt file to your server, winscp?)
  * java -jar jodconverter-cli-2.2.0.jar -f pdf testme.ppt
  * The result normally should be like below
```
com.artofsolving.jodconverter.openoffice.connection.AbstractOpenOfficeConnection connect
INFO: connected
com.artofsolving.jodconverter.openoffice.connection.AbstractOpenOfficeConnection disposing
INFO: disconnected
```

  1. Login to openmeetings and check the conversion from there too.
  * Be sure that you select correct file type to convert. For example, pdf files NOT converted by OO but with swftools pdf2swf. Check available file formats from http://www.artofsolving.com/opensource/jodconverter

> ### Check List, Pay ATTENTION to this List!!! ###
  * run ooo.sh as root otherwise converted document cant be written.
  * Dont mess with directory permissions of upload, uploadtemp and jod. DO NOT left any file world writable on your behind.
  * If your app name is other then "openmeetings" you should find
webapp/src/app/org/openmeetings/app/remote/Application.java line 66 and change to your context (will be fixed in svn)
  * check webapps/openmeetings/jod/ dir. You'll see
    * BATCHTHUMB\_date\_time\_stamp\_thumbnail.sh
    * PDFCONVERT\_date\_time\_stamp\_jodconverter.sh
    * SWFCONVERT\_date\_time\_stamp\_swfconverter.sh
    * THUMB\_date\_time\_stamp\_thumbnail.sh
  * Those files created **on the fly**. Do not attempt to play with jodconverter2.sh
  * Check command line error codes;
    * com.sun.star.task.ErrorCodeIOException; Be sure all packages installed, check ooo.sh run by root.
    * com.sun.star.lang.IllegalArgumentException: URL seems to be an unsupported one; Be sure all packages installed.
  * Always watch openmeetings.log to see what really goes on. `tail -f  /usr/lib/red5/openmeetings.log` from your console.


> ### Todo List ###
  * Create Debian init script for safe oOo operation
  * Understand this, why numbered list and bullet item couldnt be used together and always you have repeated number one in wiki :)

# Install Open Office Service on Debian/(K)Ubuntu (versions > 2.3) #
  1. Install OpenOffice-Headless as root (through su, sudo, etc.):
```
sudo apt-get install openoffice.org-headless
```
  1. Create and edit a file named openoffice.sh (for example) with you favorite editor over /etc/init.d:
```
vi /etc/init.d/openoffice.sh
```
```
#!/bin/bash
# openoffice.org  headless server script
#
# chkconfig: 2345 80 30
# description: headless openoffice server script
# processname: openoffice
# 
# Author: Vic Vijayakumar
# Modified by Federico Ch. Tomasczik
#
OOo_HOME=/usr/bin
SOFFICE_PATH=$OOo_HOME/soffice
PIDFILE=/var/run/openoffice-server.pid

set -e

case "$1" in
    start)
    if [ -f $PIDFILE ]; then
      echo "OpenOffice headless server has already started."
      sleep 5
      exit
    fi
      echo "Starting OpenOffice headless server"
      $SOFFICE_PATH -headless -nologo -nofirststartwizard -accept="socket,host=127.0.0.1,port=8100;urp" & > /dev/null 2>&1
      touch $PIDFILE
    ;;
    stop)
    if [ -f $PIDFILE ]; then
      echo "Stopping OpenOffice headless server."
      killall -9 soffice && killall -9 soffice.bin
      rm -f $PIDFILE
      exit
    fi
      echo "Openoffice headless server is not running."
      exit
    ;;
    *)
    echo "Usage: $0 {start|stop}"
    exit 1
esac
exit 0
```
  1. Change the permssions to this file:
```
chmod 0755 /etc/init.d/openoffice.sh
```
  1. Install openoffice.sh init script links:
```
update-rc.d openoffice.sh defaults
```
  1. Start the service:
```
/etc/init.d/./openoffice.sh start
```
  1. You can see if openofice is running with this command:
```
netstat -nap | grep office
```
You should get something like this:
```
tcp        0      0 127.0.0.1:8100          0.0.0.0:*
LISTEN     2467/soffice.bin 
```

  * Service openoffice start automatically when you restart the next time your server.

**Note:** This script is based on Vic Vijayakumar's script at http://little.bluethings.net/2008/05/30/automating-document-conversion-in-linux-using-jodconverterooo/


# Install Open Office Service on Debian/(K)Ubuntu Old Versions prior to 2.3 #
  1. Install OpenOffice
```
sudo apt-get install xvfb openoffice.org
```
  1. Edit /usr/lib/openoffice/share/registry/data/org/openoffice/Setup.xcu
  1. After <node oor:name="Office"> insert the following lines:
```
<prop oor:name="ooSetupConnectionURL" oor:type="xs:string">
   <value>socket,host=localhost,port=8100;urp</value>
</prop>
```
  1. Launch :
```
xvfb-run --server-args='-screen 0 800x600x16' -a /usr/lib/openoffice/program/soffice -headless -nologo -norestore
```

NOTE: Since OpenOffice 2.3 comes already with an build-in FrameBuffer there is no need for xvfb anymore.


# Mac OSx #
Install Open-Office from the Open-Office webside
Normally it is installed into your Application folder.
Goto file:
/Applications/OpenOffice.org\ 2.0.app/Contents/MacOS/share/registry/data/org/openoffice/Setup.xpu

add the following after the node **<node oor:name="Office">**:
```
                <prop oor:name="ooSetupConnectionURL">                                                                                                                               
                        <value>                                                                                                                                                      
                            socket,host=localhost,port=8100;urp;StarOffice.ServiceManager                                                                                            
                        </value>                                                                                                                                                     
                </prop>  
```

start x11/x-server (you can just start openoffice the normal way, close the openoffice app again but do NOT close the x-server/x-terminal, it will open a display for x11 on display :0)

open a new terminal and type:

sudo /Applications/OpenOffice.org\ 2.0.app/Contents/MacOS/program/soffice -display :0 -headless -nologo -norestore

OR

/Applications/OpenOffice.org\ 2.0.app/Contents/MacOS/program/soffice -display :0 -headless -nologo -norestore

sudo depends of your Red5 service .. if red5 is started as sudo (which must be started as sudo if the server is listening on port 80 (or smaller 1200) then ooservice also has to run as sudo cause it writes/reads from directories which red5 creates.

# Windows #
  * Install Open Office
  * Launch it one time (so that you have filled the registrering form of Open Office which does only popup one time)
  * Open a Command-Prompt (CMD) and goto your Open Office Executable directory (for example <sub>C:\Programme\OpenOffice.org 2.0\program</sub>)
  * Run Open Office in headless Modus with this command:
> <sub>soffice.exe -headless -nologo -norestore -accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager</sub>
  * check if its running by typing: <sub>netstat -anp tcp</sub>
> you should find a line:
> > <sub>TCP    127.0.0.1:8100         0.0.0.0:0              LISTENING</sub>

For more info see:
http://www.artofsolving.com/node/11


# Links #


  * http://www.artofsolving.com/node/10
  * http://wiki.conzul.de/index.php/OpenOffice2.x
  * http://www.artofsolving.com/opensource/jodconverter
  * http://packages.debian.org/etch-backports/openoffice.org