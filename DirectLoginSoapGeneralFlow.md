**Content / Index**

> 


## Summary ##

The trick about the DirectLogin-Flow (SSO) is that you can go into a room directly without having any OpenMeetings account.
The Single Sign On process is a token-based process where you first request a API-HASH, then you validate the HASH, then you can access the API-methods to create conference rooms or produce another HASH that can be append to the URL to access a conference room directly.

There is a special user-level in OpenMeetings called "SOAP-Only" you should use such an account if you plan to integrate OpenMeetings in you website and need to save the user/pass of the API user in your PHP/Perl/Python website.

## Recommended Scenario ##

You would like that people can create a conference room via your Application and return to that room several times. You only need to store the room-Id in your application logic, and the authentication data if the OpenMeetings admin user to manage the SOAP connection.

If a user wants to directly login into that room you should use this sequence:
  1. Request a general API-token: SOAP/REST API Calls: getSession
  1. Validate the API-token: SOAP/REST API Calls: loginUser
  1. Generate a URL-token: SOAP/REST API Calls: setUserObjectAndGenerateRoomHash
  1. Login into the Room, by adding the URL-token from setUserObjectAndGenerateRoomHash to the URL

The methods getSession/loginUser/setUserObjectAndGenerateRoomHash should be invoked by a Server-Side Script! Calling them directly from the Client is NOT recommended. Cause you don't want to share the USER/PASS for the loginUser-API-Call with the client, the client only should get the URL-token. This token is only one-time valid (or several times depending on which API call is in use). And that way the client cannot abuse the API to compromise your OpenMeetings server.

The HASH of setUserObjectAndGenerateRoomHash is only one time valid! For security reasons, otherwise people could take that link and send it by email and so flood the system with not-authorized access.

If the Room needs password protection, then this must be implemented in the Application-Logic while managing the Room (Application Logic of the environment). Also this environment has to take care that the room will be deleted if it is not needed anymore.

URL for direct Room access: (secureHash=6c280410c96e91ea05d25e0b28b10a87 MUST be replaced with a valid HASH you create using the SOAP Gateway Method setUserObjectAndGenerateRoomHash)

**Important Note:**<br />
Starting with OpenMeetings 1.1 you should use the Link like this:
http://$YOUR_RED5_HOST:$YOUR_RED5_HTTP_PORT/openmeetings/?secureHash=6c280410c96e91ea05d25e0b28b10a87
(there is no typo here!! openmeetings/?param=value => that is the format of the URL!)

Older Versions of OpenMeetings:
http://$YOUR_RED5_HOST:$YOUR_RED5_HTTP_PORT/openmeetings/main.swf8.swf?secureHash=6c280410c96e91ea05d25e0b28b10a87&language=4&lzproxied=solo
or

(if the language\_id is missing it will use the default\_language\_id from the configuration section)

## How to set up a user as default Moderator in a Room ##

You may want that the Role from your system is transfered to OpenMeetings, so that for example in a ELearning application a teacher automatically is an Moderator.
To force normal Users to wait in a room until the moderator has arrived you need to create the room via the method addRoomWithModeration and set the param **isModeratedRoom** to true.
To generate a HASH for a User that then automatically is Moderator you set the param **becomeModeratorAsInt** to 1 in the Method setUserObjectAndGenerateRoomHash


## General Flow Graph ##
![http://openmeetings.googlecode.com/svn/docs/SOAPFlowDirectLogin.png](http://openmeetings.googlecode.com/svn/docs/SOAPFlowDirectLogin.png)


## Other ways to create a Room Hash - Invitation Hashs ##

Instead of creating room-hash that are intend for direct usage you might want to create some invitation hashs to send them to users via EMail.

You can use the Method: [getInvitationHash](http://code.google.com/p/openmeetings/wiki/SoapMethods?ts=1266593733&updated=SoapMethods#getInvitationHash) to either just create an hash with limited access or password protection or you can optionally also directly send the Hash also by Mail through OpenMeetings with [sendInvitationHash](http://code.google.com/p/openmeetings/wiki/SoapMethods#sendInvitationHash).


<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />