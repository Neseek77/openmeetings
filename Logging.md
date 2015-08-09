# Server Side Logging #

## Red5 Version < or equal 0.6.3 ##

no need to change Logging in red5-0.6.3.
You can edit the Logging settings of OpenMeetings in:

$OpenMeetings-Home/WEB-INF/log4j.properties

(this enables webapp-specific logging, you can change the logging in the log4j.properties of the OpenMeetings-webapp)

## Red5 Version > 0.6.3 (Latest Trunk) ##

Todo: [Issue 323](https://code.google.com/p/openmeetings/issues/detail?id=323)

comment out this part of $OpenMeetings-Home/WEB-INF/web.xml. Otherwise Red5-0.7 will not start correctly.

```
	<context-param>
	    <param-name>log4jConfigLocation</param-name>
	    <param-value>/WEB-INF/log4j.properties</param-value>
	</context-param>
```

see also:
http://jira.red5.org/confluence/display/docs/Logging+Setup


## OpenLaszlo Client Log ##

use the debug-version (maindebug.lzx) and watch the Laszlo-Debugger
You can change the log - level for more detailed log about rpc-calls by comment out (meaning remove // )
from
```
<method name="doCall">
        if (this.showLoading) canvas._loadingAll.setAttribute('visible',true);
		//Debug.write("doCall: ",this.funcname);
		this.call();
	</method>
```
to
```
<method name="doCall">
        if (this.showLoading) canvas._loadingAll.setAttribute('visible',true);
		Debug.write("doCall: ",this.funcname);
		this.call();
	</method>
```
in
http://openmeetings.googlecode.com/svn/branches/dev/laszlo/client/xmlcrm/videoconference/xmlcrm/hibernate/netremotecallhib.lzx

that way you see all remotecalls before send to the serer