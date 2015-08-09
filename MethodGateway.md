This is just a Servlet which take the most simple values of the SOAP-Gateway to handle a Direct-Login.
You only need to pass the variables as Get-Values with the URL.

You can de-serialize the XML to Objects again using XStram, but you need the openmeetings.jar in your classpath.


## Method getSession ##

http://localhost:5080/openmeetings/MethodGateway?service=userservice&method=getSession

Response:
```
<org.openmeetings.app.hibernate.beans.basic.Sessiondata>
  <id>3</id>
  <session__id>31807001b19c9d70b91c165a3cc1c3f0</session__id>
  <starttermin__time class="sql-timestamp">2008-09-18 17:37:27.0</starttermin__time>
  <refresh__time class="sql-timestamp">2008-09-18 17:37:27.0</refresh__time>
</org.openmeetings.app.hibernate.beans.basic.Sessiondata>
```

## Method loginUser ##

http://localhost:5080/openmeetings/MethodGateway?service=userservice&method=loginUser&SID=31807001b19c9d70b91c165a3cc1c3f0&username=admin&userpass=admin
[course the values of SID,username,userpass must be set to values](of.md)
username,userpass MUST be a valid OpenMeetings ADMINISTRATION account. This Function is used to _mark_ the passed Session-Token as logedin. After this function you can use the SID to invoke addRoom or setUserObject

Response in case of success:
```
<long>1</long>
```

## Method setUserObject ##

http://localhost:5080/openmeetings/MethodGateway?service=userservice&method=setUserObject&SID=31807001b19c9d70b91c165a3cc1c3f0&username=guest&firstname=firstname&lastname=lastname&profilePictureUrl=&email=example@flocki.org
[course the values of SID,username,... must be set to values](of.md)

## Method addRoom ##

http://localhost:5080/openmeetings/MethodGateway?service=roomservice&method=addRoom&SID=75fc7d85e0d8a8ab2ce9e5c196ab724c&name=testRoom&roomtypes_id=1&comment=&numberOfPartizipants=4&ispublic=true&videoPodWidth=270&videoPodHeight=280&videoPodXPosition=2&videoPodYPosition=2&moderationPanelXPosition=400&showWhiteBoard=true&whiteBoardPanelXPosition=276&whiteBoardPanelYPosition=2&whiteBoardPanelHeight=592&whiteBoardPanelWidth=660&showFilesPanel=true&filesPanelXPosition=2&filesPanelYPosition=284&filesPanelHeight=310&filesPanelWidth=270

note that the param service is now roomservice
for the values see also SOAP Gateway Method [addRoom](http://code.google.com/p/openmeetings/wiki/SoapMethods#addRoom)