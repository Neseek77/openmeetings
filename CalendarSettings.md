In general the timezone is configured in the user profile of each user, so changing the server timezone has no effect how to the users calendar for example.

However there are some circumstances where there is no user profile available.

## Changing servers default timzeone for Emails and Invitations (display) ##

Goto Administration > Configuration and change the config-key: `default.timezone`

Valid timezone have to meet standards!

## Changing first day in week for calendar from Sunday to Monday ##

in the config.xml change the key:
```
<!--
	First day in the Week used by the Calendar
	0 means Sunday
	1 means Monday
	this is the way how the Celendar inits a week
-->
<firstdayinweek>0</firstdayinweek>	
```

## Changing the default number of max participants of a conference room created via calendar ##

Goto Administration > Configuration and change the config-key: `calendar.conference.rooms.default.size`