## PHP ##

see the sample class with almost all important methods implemented:
http://code.google.com/p/openmeetings/source/browse/trunk/plugins/moodle_plugin/openmeetings_gateway.php

## Classic ASP ##

followed up by this discussion:
http://groups.google.com/group/openmeetings-user/browse_thread/thread/d1a95748e7978a56

```
getsession:

<%
dim sid
'sid = session("sid")
Set oXmlHTTP = CreateObject("Microsoft.XMLHTTP")
oXmlHTTP.Open "POST", "http://216.161.170.249:5080/openmeetings/
services/UserService?WSDL", False

oXmlHTTP.setRequestHeader "Content-Type", "application/soap+xml;
charset=utf-8"
oXmlHTTP.setRequestHeader "SOAPAction", "http://localhost:5080/
openmeetings/services/getSession"

SOAPRequest = _
 "<?xml version=""1.0"" encoding=""utf-8""?>" & _
 "<soap12:Envelope xmlns:xsi=""http://www.w3.org/2001/XMLSchema-
instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema""
xmlns:soap12=""http://www.w3.org/2003/05/soap-envelope"">" & _
   "<soap12:Body>" & _
"<ns2:getSession xmlns:ns2=""http://
services.axis.openmeetings.org"">"& _
"</ns2:getSession></soap12:Body>" & _
"</soap12:Envelope>"

oXmlHTTP.send SOAPRequest
strResult = oXmlHTTP.responseText
'response.write strResult


 If oXmlHTTP.status = 200 Then
    If Not oXmlHTTP.responseXML Is Nothing And _
       Not oXmlHTTP.responseXML.documentElement Is Nothing And _
       oXmlHTTP.responseXML.parseError.errorCode <> 0 Then
       Set ResponseXML = oXmlHTTP.responseXML
       WellFormed = True

    Else
      Set ResponseXML = Server.CreateObject("Msxml2.DOMDocument.3.0")
      ResponseXML.async = False
      WellFormed = ResponseXML.load(oXmlHTTP.responseStream)



'getResponse and determine next action

Set objLst = ResponseXML.getElementsByTagName("*")

  Set objNodeList = ResponseXML.getElementsByTagName("*")
  For i = 0 To (objNodeList.length - 1)
  If objNodeList.item(i).nodeName = "ax218:session_id" Then

       SID = objNodeList.Item(i).text & vbCrLf
       session("SID")= SID
       'response.write "sid= "&sid&"<BR>"
     Exit For
   End If

  Next
  End If

End IF
%>

loginUser:

<%
dim sid
sid = session("sid")
Set oXmlHTTP = CreateObject("Microsoft.XMLHTTP")
oXmlHTTP.Open "POST", "http://216.161.170.249:5080/openmeetings/
services/UserService?WSDL", False

oXmlHTTP.setRequestHeader "Content-Type", "application/soap+xml;
charset=utf-8"
oXmlHTTP.setRequestHeader "SOAPAction", "http://localhost5080/
openmeetings/services/UserService/loginUser"

SOAPRequest = _
 "<?xml version=""1.0"" encoding=""utf-8""?>" & _
 "<soap12:Envelope xmlns:xsi=""http://www.w3.org/2001/XMLSchema-
instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema""
xmlns:soap12=""http://www.w3.org/2003/05/soap-envelope"">" & _
   "<soap12:Body>" & _
"<ns2:loginUser xmlns:ns2=""http://services.axis.openmeetings.org"">"&
_
"<ns2:SID>"&sid&"</ns2:SID>"& _
"<ns2:username>admin</ns2:username>"& _
"<ns2:userpass>xxxxxxxxxx</ns2:userpass>"& _
"</ns2:loginUser></soap12:Body>" & _
"</soap12:Envelope>"

oXmlHTTP.send SOAPRequest
strResult = oXmlHTTP.responseText
'response.write strResult


 If oXmlHTTP.status = 200 Then
    If Not oXmlHTTP.responseXML Is Nothing And _
       Not oXmlHTTP.responseXML.documentElement Is Nothing And _
       oXmlHTTP.responseXML.parseError.errorCode <> 0 Then
       Set ResponseXML = oXmlHTTP.responseXML
       WellFormed = True

    Else
      Set ResponseXML = Server.CreateObject("Msxml2.DOMDocument.3.0")
      ResponseXML.async = False
      WellFormed = ResponseXML.load(oXmlHTTP.responseStream)



'getResponse and determine next action

Set objLst = ResponseXML.getElementsByTagName("*")

  Set objNodeList = ResponseXML.getElementsByTagName("*")
  For i = 0 To (objNodeList.length - 1)
  If objNodeList.item(i).nodeName = "ns:return" Then

       strResponse = objNodeList.Item(i).text & vbCrLf
       session("strResponse")= strResponse
     Exit For
   End If

  Next
  End If

If strResponse <> 1 then
          nexturl= "omgetErrorbyTypeSGmethod.asp?Err="&strResponse
          response.redirect(nexturl)
End if

End IF
%>

setuserobjectandgeneratehash for direct login


<%
dim sid
sid = session("sid")
Set oXmlHTTP = CreateObject("Microsoft.XMLHTTP")
oXmlHTTP.Open "POST", "http://localhost:5080/openmeetings/services/
UserService?WSDL", False

oXmlHTTP.setRequestHeader "Content-Type", "application/soap+xml;
charset=utf-8"
oXmlHTTP.setRequestHeader "SOAPAction", "http://216.161.170.249:5080/
openmeetings/services/UserService/setUserObjectAndGenerateRoomHash"

SOAPRequest = _
 "<?xml version=""1.0"" encoding=""utf-8""?>" & _
 "<soap12:Envelope xmlns:xsi=""http://www.w3.org/2001/XMLSchema-
instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema""
xmlns:soap12=""http://www.w3.org/2003/05/soap-envelope"">" & _
   "<soap12:Body>" & _
"<ns2:setUserObjectAndGenerateRoomHash xmlns:ns2=""http://
services.axis.openmeetings.org"">"& _
"<ns2:SID>"&sid&"</ns2:SID>"& _
"<ns2:username>test</ns2:username>"& _
"<ns2:firstname>demo</ns2:firstname>"& _
"<ns2:lastname>user</ns2:lastname>"& _
"<ns2:profilePictureUrl />"& _
"<ns2:profilePictureUrl />"& _
"<ns2:email>"&stremail&"</ns2:email>"& _
"<ns2:externalUserId />"& _
"<ns2:externalUserType />"& _
"<ns2:room_id>1</ns2:room_id>"& _
"<ns2:becomeModeratorAsInt>1</ns2:becomeModeratorAsInt>"& _
"<ns2:showAudioVideoTestAsInt>1</ns2:showAudioVideoTestAsInt>"& _
"</ns2:setUserObjectAndGenerateRoomHash></soap12:Body>" & _
"</soap12:Envelope>"

oXmlHTTP.send SOAPRequest
strResult = oXmlHTTP.responseText
response.write strResult


 If oXmlHTTP.status = 200 Then
    If Not oXmlHTTP.responseXML Is Nothing And _
       Not oXmlHTTP.responseXML.documentElement Is Nothing And _
       oXmlHTTP.responseXML.parseError.errorCode <> 0 Then
       Set ResponseXML = oXmlHTTP.responseXML
       WellFormed = True

    Else
      Set ResponseXML = Server.CreateObject("Msxml2.DOMDocument.3.0")
      ResponseXML.async = False
      WellFormed = ResponseXML.load(oXmlHTTP.responseStream)



'getResponse and determine next action

Set objLst = ResponseXML.getElementsByTagName("*")

  Set objNodeList = ResponseXML.getElementsByTagName("*")
  For i = 0 To (objNodeList.length - 1)
  If objNodeList.item(i).nodeName = "ns:return" Then

       strroomHashby = objNodeList.Item(i).text & vbCrLf
       session("strroomHASH")= strroomHASH
     Exit For
   End If

  Next
  End If

End IF
%>
```