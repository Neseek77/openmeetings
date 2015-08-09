If you need to know what version of OpenMeetings you are running, you can check that by the file: openmeetings.jar


Unzip: /webapps/openmeetings/WEB-INF/lib/openmeetings.jar (jars are zips)<br />
In the resulting unziped folder open the file to read:
/META-INF/MANIFEST.MF

You get something like this:
```
Manifest-Version: 1.0
Ant-Version: Apache Ant 1.8.2
Created-By: 1.6.0_26-b03-383-11A511 (Apple Inc.)
Built-By: OpenMeetings - http://openmeetings.googlecode.com
Built-On: 23-October-2011
Svn-Revision: 4368
```

This version info is available since version 1.8.0. If you have an older version as version 1.8.0 then you might simply check the language files and the labelid in there, the numbers of labels are almost always unique in the versions.