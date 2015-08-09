## Changing Labels and text strings ##

All language string should be localized and stored in the language section. You have a full featured language editor with every installation of OpenMeetings, check out the LanguageEditor.

To lookup the labelids in the GUI you can simply run OpenMeetings client with the debug enabled. That way every text-string has the labelid in brackets additionally in the textfield.
To start the client in debug mode just add the param ?swf=maindebug.swf8.swf to the URL.
Example: http://localhost:5080/openmeetings/?swf=maindebug.swf8.swf

## Changing layout, adding footers in emails ##

If you would like to change the standard email layout or add for example some footer in every email you have to change the email templates.

To do that: Stop the Red5/OpenMeetings-Service
Goto: $Red5\_HOME/webapps/openmeetings/WEB-INF/lib/openmeetings-templates.jar
Unzip: openmeetings-templates.jar (this is a usual ZIP file, you can rename it to openmeetings-templates.zip unzip, edit the files, zip it again and rename it to openmeetings-templates.jar)

In openmeetings-templates.jar there are all template files. They have the file extension .vm (VM stands for velocity-template).
After you have done your changes you need to (re)start the Red5/OpenMeetings-Service so that changes are online.