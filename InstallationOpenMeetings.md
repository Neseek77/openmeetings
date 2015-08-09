<b><font color='#FF0000'> OpenMeetings moves to Apache Foundation, update your bookmarks to the new project page:<br /><a href='http://incubator.apache.org/openmeetings/'><a href='http://incubator.apache.org/openmeetings/'>http://incubator.apache.org/openmeetings/</a></a> </font></b>

---


To update from past versions or migrate see UpdateOpenMeetings, for payed services see [Service and Hosting](http://www.openmeetings.de/hosting-and-service/)

[![](http://openmeetings.googlecode.com/svn/docs/installation_instructions.png)](http://openmeetings.googlecode.com/files/openmeetings_1_9_1_r4707.zip)

  1. #### Recommendation for production environment ####
> > By default OpenMeetings uses the integrated Apache Derby database. For production environment you should consider using [MySQL](Configuration_Mysql.md), [Postgres](Configuration_Postgres.md) or for example [IBM DB2](Configuration_IBM_DB2.md) or [IBM DB2](Configuration_Oracle.md)
  1. #### Enabling Image Upload and import to whiteboard ####
    * Install **ImageMagick** on the server, you can get more information on http://www.imagemagick.org regarding installation. The instructions for installation can be found there http://www.imagemagick.org/script/binary-releases.php, however on most linux systems you can get it via your favorite package managers (apt-get it)
  1. #### Enabling import of PDFs into whiteboard ####
    * Install **GhostScript** on the server, you can get more information on http://pages.cs.wisc.edu/~ghost/ regarding installation. The instructions for installation can be found there, however on most linux systems you can get it via your favorite package managers (apt-get it).
    * Install **SWFTools** on the server, you can get more information on http://www.swftools.org/ regarding installation. Some of the Linux distributions already have it in there package manager see http://packages.debian.org/unstable/utils/swftools), the recommended version of **SWFTools** is 0.9 as prior version have a bug that does lead to wrong object dimensions in the Whiteboard
  1. #### Enabling import of .doc, .docx, .ppt, .pptx, ... all Office Documents into whitebaord ####
    * **OpenOffice-Service** started and listening on port 8100, see OpenOfficeConverter for details
  1. #### Enabling Recording and import of .avi, .flv, .mov and .mp4 into whiteboard ####
    * Install **FFMpeg**. You should get FFMPEG in an up to date copy! For Windows you can download a Build for example from http://ffmpeg.arrozcru.org/builds/ Linux or OSx Users should be able to use one of the various Installation Instructions on the Web. You need to enable libmp3lame!
    * Install **SoX** http://sox.sourceforge.net/. You should install SoX in a up to date copy! SoX 12.xx will NOT work!

## Ports, NAT Settings, Customize ##

  * see PortSettings

## VMWare ##

  * See http://nightly.openmeetings.de/openmeetings/builds/vmware/
  * VMWare 1.6.1 [blog-entry](http://openstudio.no-ip.info/2011/01/openmeetings-1-6-rc1-vmware-appliance-using-ubuntu-server-10-04-lts/)
  * [VMWareImageDebian051](VMWareImageDebian051.md)
  * [VMWareImageMoodlePackOM052](VMWareImageMoodlePackOM052.md)
  * [VMImageRev2056](VMImageRev2056.md)
  * [VMWareImageRunningOpenmeetingsV1\_0RC1](VMWareImageRunningOpenmeetingsV1_0RC1.md)

## Hardware Requirements ##

  * Minimalistic requirement (without Document Converters, Recorder and Upload feature)  1GHz CPU 1 GB RAM (_server-side_)
  * Recommended requirement 2x/4x 2GHz ++ CPU (32 or 64Bit) 4GB RAM. If you have a 64Bit Operating System check if there is a OpenOffice 64Bit Version [available for your OS](http://download.openoffice.org/other.html#de) (_server-side_)
  * Headset/Microphone recommendation: Logitech ClearChat PC Wireless, for example from [Amazon](http://www.amazon.de/Logitech-ClearChat-kabelloser-Kopfh%C3%B6rer-Transmitter/dp/B00166WSN4) or [EBay Auctions](http://shop.ebay.com/i.html?_nkw=Logitech+ClearChat.+Wireless&_sacat=0&_trksid=p3286.m270.l1313&LH_BIN=1&LH_IncludeSIF=1&_odkw=Logitech+ClearChat+PC&_osacat=0) (_cient-side_). [See all recommendations](RecommendedHardwareList.md) for cameras, headsets and speakerphones.

## Debian, Links, Changelog, VoIP ##

  * for VoIP and SIP Integration see also [VoIPAndSIP](VoIPAndSIP.md)
  * [Ubuntu Live DVD](MeetingsLive.md) MeetingsLive http://meetingslive.sourceforge.net/
  * [Installation on CentOS 5](InstallationCentOS5.md)
  * [Installation on RHELS 5.6](InstallationRHELS.md)
  * [Instalar Openmeetings en Ubuntu 10.04 (Spanish)](OpenmeetingsEnUbuntu.md)
  * [Install openmeetings on Ubuntu 10.04 LTS](UbuntuLucidLTS.md)
  * [Espanol Version for Debian Installation](http://liberamemoria.blogspot.com/2009/02/instalando-openmeetings-07-rc-2-en.html)
  * [Portuguese Version for Debian Installation of 0.9 RC1](http://aitinet.com/blog/wp-content/uploads/2009/08/TUTORIAL-PARA-INSTALACAO-DO-RED5-E-OPENMEETINGS-NO-LINUX-DEBIAN-LENNY-51.pdf)
  * [Automatic script installation for Debian](http://code.google.com/p/openmeetings/wiki/Automatic_script_installation_for_Debian)
  * InstallationDebian
  * [qosTips](qosTips.md)
  * French version of Installation Instructions: http://flash.54n.free.fr/?/Root/Debian/OpenMeetings-Installation

## Hidden and experimental features ##

  * SSL encryption, search the mailing list for "native SSL".
  * if you resize your own video-pod in the conference much larger and then synchronize  the stream, all clients will get a better picture (it also needs more bandwidth, as it will take the actual new height / width numbers to publish your stream)
  * there are some hidden emoticons (for example (activate)), you might want to add your own ones [Full List](http://openmeetings.googlecode.com/svn/trunk/webapp/webapp/openmeetings/public/emoticons/emotes.xml)

## Integration, Single Sign On and LDAP ##

  * To Integrate OpenMeetings it is recommended to use the [SOAP-Gateway](http://code.google.com/p/openmeetings/wiki/SoapMethods)
  * Regarding LDAP-Configuration see the Installer Script and sample Configuration
  * [Configure LDAP Authentication with Active Directory](ActiveDirectoryLDAP.md)

## using 3th Party Publishing Software, RTP-Streams or IP-Cams ##

  * If you want to integrate 3th Party Software for publishing Live-Video you may have a look at [Xuggler](http://www.xuggle.com). There is no out of the Box solution but there are already people successfully publish their usual [RTP-LiveStream](http://www.red5wiki.com/wiki/SteamStream) by using Xuggler

## Tips and Tricks ##

  * Use the Debug-Application: Enter in your browser http://$RED-HOST:$RED5-PORT/openmeetings/maindebug.lzx.lzr=swf8.swf for example http://localhost:5080/openmeetings/maindebug.lzx.lzr=swf8.swf
  * If you have Problems with conversion-jobs check your Red5-Log output. There will be error Messages which help you. You must install OpenOffice, ImageMagick, GhostScript and SWFTools to run all conversion-Jobs correctly.
  * if you have problems in converting Files check the Batch-Scripts in $OPENMEETINGS\_HOME/jod
  * If you want to use red5 as a service (without red5.bat), you need to move red5\wrapper\wrapper.exe to red5\wrapper.exe. Then change some path in conf\wrapper.conf (like ..\lib\foo.jar to lib\foo.jar). And finally change the register key in HKEY\_LOCAL\_MACHINE\SYSTEM\CurrentControlSet\Services\Red5\ImagePath to point to the new wrapper.
  * you can query a Mysql Database to get the schema\_collection by using this query:
```
SELECT Table_name, TABLE_COLLATION FROM information_schema.tables WHERE table_schema = 'openmeetings' ORDER BY table_name DESC
```