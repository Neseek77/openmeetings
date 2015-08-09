There is a MySQL sample config: postgres\_persistance.xml that ships with every release of OpenMeetings.

### Before you start ###

  * Make sure that you have set utf8 as default/server-character-set
  * make sure Postgres is listening on TCP/IP connections!

To verify if Postgres connection is working: OpenMeetings will automatically create all tables in the database.

  * If you encounter issues, you can drop the db again and delete the file $red5/webapps/openmeetings/conf/install.xml and then run the web based installer again

### Steps todo ###

  1. Rename<br />$red5/webapps/openmeetings/WEB-INF/classes/META-INF/postgres\_persistance.xml<br />to<br />$red5/webapps/openmeetings/WEB-INF/classes/META-INF/persistance.xml
  1. Prepare Database Settings - go to openmeetings\_version\_no\_xx/webapps/openmeetings/WEB-INF/classes/META-INF/persistance.xml
  1. Run red5-service and goto the web-based installer: http://localhost:5080/openmeetings/install