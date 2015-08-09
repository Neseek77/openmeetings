Feature available since [r4303](https://code.google.com/p/openmeetings/source/detail?r=4303) ([Issue 1403](https://code.google.com/p/openmeetings/issues/detail?id=1403)) Minimum OpenMeetings Version 1.9.x

### **Renaming `/openmeetings` _context_** ###
If you want to have `http://yourcorp.com:5080/yourmeetings` instead of `http://yourcorp.com:5080/openmeetings` you need to do the following
  1. stop openmmetings if it is running
  1. go to openmeetings install dir (for ex. `/opt/red5`)
  1. rename `/opt/red5/webapps/openmeetings` to `/opt/red5/webapps/yourmeetings` (for ex. `mv /opt/red5/webapps/openmeetings /opt/red5/webapps/yourmeetings`)
  1. open `/opt/red5/webapps/yourmeetings/config.xml` using your favorite text editor (for ex. `vim /opt/red5/webapps/yourmeetings/config.xml`)
  1. find and modify `<webAppRootKey>openmeetings</webAppRootKey>` to be `<webAppRootKey>`**yourmeetings**`</webAppRootKey>`
  1. find and modify `<httpRootKey>/openmeetings/</httpRootKey>` to be `<httpRootKey>`**/yourmeetings/**`</httpRootKey>`
That's it

### **Changing hard-coded application name in string labels** ###
To have _Your Application Name_ instead of _Openmeetings_ in **all** text labels you need to do the following
  1. ensure **`$APP_NAME`** placeholder is used in _all_ strings you have added and/or modified (all strings bundled into Openmeetings already have this placeholder)
  1. login to Openmeetings as user with administrator privileges
  1. go to `Administration -> Configuration`
  1. find and edit configuration with name **`application.name`**
  1. Set its value to be _`Your Application Name`_
  1. `Save`
  1. reload page or relogin
All string will display _`Your Application Name`_ in place of _`Openmeetings`_

Please _NOTE_ there are 3 additional properties in the config.xml file which should be modified for **TOTAL** Openmeetings clean up:
```
<currentappname>OpenMeetings</currentappname>
<currentappnameurl>http://openmeetings.googlecode.com</currentappnameurl>
<currentappnamebugurl>http://code.google.com/p/openmeetings/issues/list</currentappnamebugurl>
```
please modify them according to your needs.