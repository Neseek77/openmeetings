There is a MySQL sample config: mysql\_persistance.xml that ships with every release of OpenMeetings.

### Before you start ###

  * Make sure that you have set utf8 as default/server-character-set in your MySQL configuration (my.cnf) before you start the installation process of OpenMeetings!
  * make sure MySQL is listening on TCP/IP connections!

To verify if MySQL connection is working: OpenMeetings will automatically create all tables in the database and all tables should have utf8 as encoding/collation!

  * If you encounter issues, you can drop the db again and delete the file $red5/webapps/openmeetings/conf/install.xml and then run the web based installer again

### Steps todo ###
  1. Rename<br />$red5/webapps/openmeetings/WEB-INF/classes/META-INF/mysql\_persistence.xml<br />to<br />$red5/webapps/openmeetings/WEB-INF/classes/META-INF/persistence.xml
  1. Prepare Database Settings - go to openmeetings\_version\_no\_xx/webapps/openmeetings/WEB-INF/classes/META-INF/persistence.xml
> > MySQL-Database-Users: Alter following config values in `persistance.xml` $DB\_HOST $USER\_NAME $USER\_PASS with your values
```
<property name="openjpa.ConnectionProperties" value="DriverClassName=com.mysql.jdbc.Driver, Url=jdbc:mysql://$DB_HOST:3306/openmeetings?autoReconnect=true&amp;useUnicode=true&amp;createDatabaseIfNotExist=true&amp;characterEncoding=utf-8&amp;connectionCollation=utf8_general_ci&amp;cachePrepStmts=true&amp;cacheCallableStatements=true&amp;cacheServerConfiguration=true&amp;useLocalSessionState=true&amp;elideSetAutoCommits=true&amp;alwaysSendSetIsolation=false&amp;enableQueryTimeouts=false&amp;prepStmtCacheSize=3000&amp;prepStmtCacheSqlLimit=1000, MaxActive=100, MaxWait=10000, TestOnBorrow=true,  poolPreparedStatements=true,  Username=$USER_NAME, Password=$USER_PASS"/>
	
```
  1. Run red5-service and goto the web-based installer: http://localhost:5080/openmeetings/install