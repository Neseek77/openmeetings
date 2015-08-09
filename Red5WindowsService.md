Installing Red5 as a service on windows platform for openmeetings

# Details #

Please download the community edition wrapper from

_http://wrapper.tanukisoftware.org/doc/english/download.jsp_

Below is integration method 1 as per above site you can try other integration methods mentioned online at

_http://wrapper.tanukisoftware.org/doc/english/integrate.html_

Installing Wrapper Files
There are 3 directories which are required to be configured in order to be able to use the Wrapper.

**1) Red5 Home / Root directory**

First, please copy the following files into the Red5 Home / Root directory:

{WRAPPER\_HOME}\bin\wrapper.exe

{WRAPPER\_HOME}\src\bin\Installapp-NT.bat.in

{WRAPPER\_HOME}\src\bin\Uninstallapp-NT.bat.in


Rename the three batch files with refracting your application name as follows.
Be sure to remove the .in extensions so that the files all end in .bat.
(Depending on how your file explorer is configured on your computer, you may not be able to see file extensions.)

You should now have:


{ Red5\_HOME}\red5.bat

{ Red5\_HOME}\Installred5-NT.bat

{ Red5\_HOME}\Uninstallred5-NT.bat


The wrapper.exe file is the actual Wrapper executable.
The three batch files are used to run Red5 in a console, and to install and uninstall it as a Windows Service.
These batch files should not require any modification.
They do assume that the wrapper.conf file will be located within a conf directory (one level up, ../conf/wrapper.conf).

We need to edit 2 batch files as mentioned below.

Open the files { Red5\_HOME}\Installred5-NT.bat and { Red5\_HOME}\Uninstallred5-NT.bat using notepad and edit the lines

> set _WRAPPER\_CONF="%_REALPATH%..\conf\wrapper.conf"

change to

> set _WRAPPER\_CONF="%_REALPATH%conf\wrapper.conf"

**2) lib directory**

Copy the following two files into the Red5 lib directory:


{WRAPPER\_HOME}\lib\wrapper.dll

{WRAPPER\_HOME}\lib\wrapper.jar


The wrapper.dll file is a native library required by the portion of the Wrapper which runs within the JVM. The wrapper.jar file contains all of the Wrapper classes.

**3) conf directory**

The Wrapper requires a configuration file wrapper.conf for each application. The standard location for this file is in a conf directory in the application's home directory. Red5  does not have such a directory by default, so we will need to create one. Please copy the following template file wrapper.conf.in into the conf directory of Red5.


{WRAPPER\_HOME}\src\conf\wrapper.conf.in


Rename the file as follows. Be sure to remove the .in extension so that the file is named wrapper.conf.

You should now have:


{ Red5\_HOME}\conf\wrapper.conf


If you wish to relocate the configuration file wrapper.conf, you are free to do so. You will need to modify the batch files copied into the bin directory above, to reflect the new location.

below is a sample wrapper.conf file for better understanding you can refer the same.

**NOTE :**the text formatting of below file is not proper due to restrictions of creating this wiki page

**# Sample wrapper.conf file start
# Copy below this line**

#
# Wrapper Properties
#
# Java Application

wrapper.java.command=java

# Java Main class.  This class must implement the WrapperListener interface
#  or guarantee that the WrapperManager class is initialized.  Helper
#  classes are provided to do this for you.  See the Integration section
#  of the documentation for details.
wrapper.java.mainclass=org.tanukisoftware.wrapper.WrapperSimpleApp

# Java Classpath (include wrapper.jar)  Add class path elements as
#  needed starting from 1
wrapper.java.classpath.1=lib/wrapper.jar
wrapper.java.classpath.2=conf
wrapper.java.classpath.3=boot.jar

# Java Library Path (location of Wrapper.DLL or libwrapper.so)
wrapper.java.library.path.1=lib

# Java Additional Parameters
wrapper.java.additional.1=-Xrs
wrapper.java.additional.2=-XX:+AggressiveOpts
wrapper.java.additional.3=-XX:+DisableExplicitGC
wrapper.java.additional.4=-Djava.net.preferIPv4Stack=true
wrapper.java.additional.5=-Dlogback.ContextSelector=org.red5.logging.LoggingContextSelector
wrapper.java.additional.6=-Dcatalina.useNaming=true
wrapper.java.additional.7=-Dpython.home=lib
wrapper.java.additional.8=-Xverify:none

# Initial Java Heap Size (in MB)
wrapper.java.initmemory=256

# Maximum Java Heap Size (in MB)
wrapper.java.maxmemory=768

# Application parameters.  Add parameters as needed starting from 1
wrapper.app.parameter.1=org.red5.server.Bootstrap

#
# Wrapper Logging Properties
#
# Format of output for the console.  (See docs for formats)
wrapper.console.format=PM

# Log Level for console output.  (See docs for log levels)
wrapper.console.loglevel=INFO

# Log file to use for wrapper output logging.
wrapper.logfile=log/red5\_service.log

# Format of output for the log file.  (See docs for formats)
wrapper.logfile.format=LPTM

# Log Level for log file output.  (See docs for log levels)
wrapper.logfile.loglevel=INFO

# Maximum size that the log file will be allowed to grow to before
#  the log is rolled. Size is specified in bytes.  The default value
#  of 0, disables log rolling.  May abbreviate with the 'k' (kb) or
#  'm' (mb) suffix.  For example: 10m = 10 megabytes.
wrapper.logfile.maxsize=0

# Maximum number of rolled log files which will be allowed before old
#  files are deleted.  The default value of 0 implies no limit.
wrapper.logfile.maxfiles=0

# Log Level for sys/event log output.  (See docs for log levels)
wrapper.syslog.loglevel=NONE

#
# Wrapper Windows Properties
#
# Title to use when running as a console
wrapper.console.title=Red5

#
# Wrapper Windows NT/2000/XP Service Properties
#
# WARNING - Do not modify any of these properties when an application
#  using this configuration file has been installed as a service.
#  Please uninstall the service before modifying this section.  The
#  service can then be reinstalled.

# Name of the service
wrapper.ntservice.name=Red5

# Display name of the service
wrapper.ntservice.displayname=Red5

# Description of the service
wrapper.ntservice.description=Red5 Open Source Flash Server

# Service dependencies.  Add dependencies as needed starting from 1
wrapper.ntservice.dependency.1=

# Mode in which the service is installed.  AUTO\_START or DEMAND\_START
wrapper.ntservice.starttype=AUTO\_START

# Allow the service to interact with the desktop.
wrapper.ntservice.interactive=true

**# Copy till this line
# Sample wrapper.conf file end**