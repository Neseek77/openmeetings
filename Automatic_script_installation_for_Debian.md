# Automatic Script Installation for Debian 5 #

**What is?**

It is a shell script, what resolve all dependencies, install init scripts, download the latest stable version of Openmeetings (1.1 [r3087](https://code.google.com/p/openmeetings/source/detail?r=3087)), and install every things "automagically"


**How it works?**


_First_, the script do a copy of /etc/sources.list and add new Debian Repositories... then adds a gpg key from debian-multimedia;

_Second_, Download and install all things needed, like java-sdk, openoffice, mysql server. After this, the init scripts are downloaded and installed and database openmeetings are created on mysql server.

_Third_, the OpenMeetings is downloaded, and copied to /opt/red5. Permissions and owners are defined. The file hibernate.cfg.xml will be created with the user defined by user.

_Fifth_, use the OpenMeetings :)



**How I can run?**

Download the script [here](http://openmeetings.googlecode.com/svn/docs/install-om-r3087.sh)
Set the execute permission: chmod +x install-om-3097.sh
I advise you, to edit the script and change the url of debian repository for your country, at variable REP\_DEBIAN. If you don't set the repository for your country, the script is using the US repository.

To run: ./install-om-3097.sh

And answer all questions and wait. This process takes approximately 20 minutes.


**To Do**

Add a way to install the new versions without changes on script;
Support for the main distribuitions (Centos, Ubuntu, SuSe)


**Fixed Issues**
Now, is not needed recompile the ffmpeg


_**P.S. This script is designated by Debian 5 users, others systems this script will not work!**_



Questions, suggestions, bugs.... anything, mail-me: victor@sartori.eti.br


Thats all Folks!!!! enjoy