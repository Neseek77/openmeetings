There are multiple Session variables:

| **Client Side name** | **Server Side name** | **description**  | **init-time / place** |
|:---------------------|:---------------------|:-----------------|:----------------------|
| canvas.streamid      | IConnection.getClient.getID() and org.xmlcrm.app.conference.videobeans.RoomClient.streamid | this is unique key for every rtmpconnection, it is the only way to get the RoomClient in the current Session, but it is not used to identify a logged-in user. It is used to hold session variables for Users and Guests while making a Conference. The RoomClient holds also data to identify who is together in a room. | invoked by Red5-Event: org.xmlcrm.app.remote.Application.roomJoin |
| conference.video.editRecordStream.broadcastId | org.xmlcrm.app.remote.Application.broadCastCounter | this is a unique key for every netstream/rtmpStream, you cannot use the streamid, cause if a user tries to reconnect his stream he cannot use the same Id for publishing his stream | org.xmlcrm.app.remote.Application.getBroadCastId invoked by editRecordStream.lzx Line 41 getBroadCastId.doCall() |
| canvas.sessionId     | No Name stored in Database | The security Token | org.xmlcrm.app.remote.MainService.getsessiondata() by xmlcrm.hibernate.hibRtmpConnection.lzx Line 70 |
| canvas.publicSID     | org.xmlcrm.app.conference.videobeans.RoomClient.publicSID | unique public SID, shared through RTMP-Reconnects, and Server-Side stored in RoomClient-Object, so this will be also send to all connected users, you cannot use the User\_id here cause Guests have no User\_id, you cannot use the canvas.sessionId for security reason | org.xmlcrm.app.remote.Application.getPublicSID and overwritePublicSID (invoked after reconnecting) both invoked by the client in hibRtmpConnection |

Issues to solve:
  * [Issue 361](https://code.google.com/p/openmeetings/issues/detail?id=361)