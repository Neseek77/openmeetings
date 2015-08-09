# Introduction #

For a detailed instruction which services, methods and params are available see the list in the [SoapMethods](SoapMethods.md). ALL methods that are implemented for the SOAP API are also available via REST.

# How to invoke Services via REST #

For example for the method getSession in the UserService, you would use this URL via REST:<br />
http://demo.openmeetings.de/openmeetings/services/UserService/getSession

Result:
```
<ns:getSessionResponse>
<ns:return type="org.openmeetings.app.hibernate.beans.basic.Sessiondata">
  <ax219:id>156971</ax219:id><ax219:language_id xsi:nil="true"/>
  <ax219:organization_id xsi:nil="true"/>
  <ax219:refresh_time>2011-07-03T14:40:44.000Z</ax219:refresh_time>
  <ax219:sessionXml xsi:nil="true"/>
  <ax219:session_id>26584705202d7c1de6cc8e7e28890e3d</ax219:session_id>
  <ax219:starttermin_time>2011-07-03T14:40:44.000Z</ax219:starttermin_time>
  <ax219:storePermanent xsi:nil="true"/>
  <ax219:user_id xsi:nil="true"/>
</ns:return>
</ns:getSessionResponse>
```

To login a user the call to auth would be:<br />
http://demo.openmeetings.de/openmeetings/services/UserService/loginUser?SID=26584705202d7c1de6cc8e7e28890e3d&username=hans&userpass=test

Result:
```
<ns:loginUserResponse>
  <ns:return>-10</ns:return>
</ns:loginUserResponse>
```

=> It does return -10, which is the error id cause we will not write down a SOAP login to our public server in this wiki.
However the correct way to check this error would be to invoke the call to get the localized error message. So we invoke the method to get the error via REST:<br />
http://demo.openmeetings.de/openmeetings/services/UserService/getErrorByCode?SID=26584705202d7c1de6cc8e7e28890e3d&errorid=-10&language_id=1

Result:
```
<ns:getErrorByCodeResponse>
<ns:return type="org.openmeetings.app.data.beans.basic.ErrorResult">
  <ax218:errmessage>Username not found</ax218:errmessage>
  <ax218:errorId>-10</ax218:errorId>
  <ax218:errortype>Error</ax218:errortype>
</ns:return>
</ns:getErrorByCodeResponse>
```