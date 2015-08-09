**Since Version 1.3 RC1** there is a System Backup and Import tool

Login as Administrator and Goto > Backup

![http://openmeetings.googlecode.com/svn/docs/system_backup.png](http://openmeetings.googlecode.com/svn/docs/system_backup.png)

The Text in the Administration>Backup section actually just says it all.
The backup contains users, rooms, organizations and appointements (Invitations and Reminders are NOT included in the Backup and Reminders will not be send again!).
The Backup contains also all uploaded Files and Documents and will re-import them again.

Max Upload Size is 1000 MB.


<br /><br /><br /><br /><br />




---


**Prior Version 1.3**



This is the recommended scenario to update openmeetings from a previous installation and to keep the users-table, configuration and rooms.

## Introduction ##

You do not need to alter any database-table for the update process, OpenMeetings will automatically check the database scheme every time you start it and alter it automatically based on the hibernate.cfg.xml.<br />
=> There is no need to check the hibernate.cfg.xml in general, but it might be useful to check if all tables are still the same, sometime we add new tables to the scheme that need to be added to your config.

The converters and all 3th Party tools don't require update. However you should keep an eye on what versions of OpenOffice, ImageMagick, GhostScript and !SWFTools you have. Especially !SWFTools latest version does fix some issues for the Presentation files.

## The Process in general ##

**This process usually takes 30-60 minutes if you have done that several times**

The process of updating is in general as following:<br />
1) Install OpenMeetings on a clean DB to import all new labels and languages. Maybe on your localhost, no need to bother your target environment with it.<br />
2) Shutdown your Prod-Server (the target server you are going to update)<br />
3) Export the tables **fieldlanguage** **fieldlanguagesvalues** **fieldvalues**( for example with phpMyAdmin, add the controls **DROP TABLE** to the exported tables so that you **overwrite** the existing table when you do import them again)<br />
4) Make a backup of the old tables from your prod server<br />
5) Import those tables from step 3) into your prod server<br />
6) Copy the dir: openmeetings/WEB-INF (actually you often only need to update openmeetings/WEB-INF/lib/openmeetings.jar and openmeetings/WEB-INF/lib/openmeetings-templates.jar) from your local  to the new openmeetings-version and overwrite the old ones<br />
7) Copy the dir: openmeetings/screen from your local to the new openmeetings-version and overwrite the old ones<br />
8) Copy the openmeetings/maindebug.swf8.swf, openmeetings/main.swf8.swf, openmeetings/index.jsp and openmeetings/config.xml from your local to the new openmeetings-version and  <br />
9) Maybe clear growing and big log-files.<br />
=> There is no need to check the hibernate.cfg.xml in general, but it might be useful to check if all tables are still the same, sometime we add new tables to the scheme that need to be added.<br />
10) restart prod and test (sometimes you need to clear your browser cache to see changes)<br />
11) You might have to change or re-import the configurations-table. For example the new screen sharing option need to be enabled in the config.<br /><br />

=> this way your user-tables are the same and you should be able to continue.<br />
<br />
## The hard way ##
<br />
If the way above is not your route, and you are not forced or willing to keep some of your configs and rooms => you can goto Administration > Backup and then export the Users.xml and import them again into your new Installation. You have to manually copy the avatars and room files from the folder **upload** into your new install dir.