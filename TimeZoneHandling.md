List of default Timezones:
http://openmeetings.googlecode.com/svn/trunk/singlewebapp/WebContent/languages/timezones.xml

To add a new Time Zone you have to lookup its represant in Java's Timezone registry.
You can then add freely new time zones to the XML file. The XML entries are imported into the database into the table **om\_timezone** during the installation of OpenMeetings. (Don't forget to stop the server before you change anything in the database).

The Servers default timezone is stored in the configuration table in the attribute **default.timezone**. The name in this value links to an attribute in the table om\_timezone in the column **jname**.

You can set a Flag **forceTimeZoneCheck** for each user that gives him a Pop-Up notification to change his time zone. The Flag can be changed in the user administration of OpenMeetings. This can be handy if you update from a previous version of OpenMeetings to make sure users see the new feature and update their settings.

### How to lookup timezone's ###

```
                    String[] ids = TimeZone.getAvailableIDs();
		    for (String id : ids) {
		    	
		    	TimeZone timeZone = TimeZone.getTimeZone(id);
		    	//TimeZone timeZone = timeRegistry.getTimeZone(id);
		    	
		    	Calendar cal = Calendar.getInstance();
				cal.setTimeZone(timeZone);
				// + cal.get(Calendar.DST_OFFSET)
				int offset = cal.get(Calendar.ZONE_OFFSET);
		    	
				int offsetInMinutes = offset/1000/60;
				
				System.out.println("<name>" + id + "</name><offset>"+offsetInMinutes+"</offset>");
		    }
```