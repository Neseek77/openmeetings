**Description**<br />
All Services here. You can see the recommended flow of embedding for example Rooms and Single Sign On into your application at
DirectLoginSoapGeneralFlow

See also for a complete Service Overview and Guide the Axis2-Service-Browser. It ships with every OpenMeetings installation so goto:
http://localhost:5080/openmeetings/axis2-web/

<b><font color='#FF3300'>All SOAP API Calls are also <a href='RestMethods.md'>available as REST-Service</a>!</font></b>

**Availible Services**<br />

> 

URL for direct Room access: (secureHash=6c280410c96e91ea05d25e0b28b10a87 MUST be replaced with a valid HASH you create using the SOAP Gateway Method setUserObjectAndGenerateRoomHash)

**Important Note:**<br />
Starting with OpenMeetings 1.1 you should use the Link like this:
http://$YOUR_RED5_HOST:$YOUR_RED5_HTTP_PORT/openmeetings/?secureHash=6c280410c96e91ea05d25e0b28b10a87
(there is no typo here!! openmeetings/?param=value => that is the format of the URL!)

Older versions like this:
http://$YOUR_RED5_HOST:$YOUR_RED5_HTTP_PORT/openmeetings/main.swf8.swf?secureHash=6c280410c96e91ea05d25e0b28b10a87&language=4&lzproxied=solo or


(if the language\_id is missing it will use the default\_language\_id from the configuration section)

for a complete Guide how to realize a single sign on or generate a URL to access a Room (or Recording) Directly read DirectLoginSoapGeneralFlow

To get a Recording instead of a Room use the method **setUserObjectAndGenerateRecordingHashByURL**


---


### UserService ###

Service-Endpoint:
http://localhost:5080/openmeetings/services/UserService?wsdl
(http://$RED5_HOST:$RED5_HTTP_HOST/openmeetings/services/UserService?wsdl)


#### getSession ####

Method: _public Sessiondata getSession()_

Description: load this session id before doing anything else
Returns an Object of Type [Sessiondata](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/basic/Sessiondata.java), this contains a sessionId, use that sessionId in all Methods

Params: no params


#### loginUser ####

Method: _public Long loginUser(String SID, String username, String userpass)_

Description: auth function, use the SID you get by [getSession](http://code.google.com/p/openmeetings/wiki/SoapMethods#getSession),
return positive means logged-in, if negative its an ErrorCode, you have to invoke the Method
[getErrorByCode](http://code.google.com/p/openmeetings/wiki/SoapMethods#getErrorByCode) to get the Text-Description of that ErrorCode

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | Username from OpenMeetings, the user has to have Admin-rights |
| String   | userpass | Userpass from OpenMeetings |


#### getErrorByCode ####

Method: _public ErrorResult getErrorByCode(String SID, Long errorid, Long language\_id)_

Description: loads an [Error-Object](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/data/beans/basic/ErrorResult.java). If a Method returns a negative Result, its an Error-id,
it needs a language\_id to specify in which language you want to display/read the error-message.
English has the Language-ID one, for different one see the [list of languages](http://openmeetings.googlecode.com/svn/trunk/webapp/webapp/openmeetings/languages/languages.xml)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| Long     | errorid  | the errorid (negative Value here!) |
| Long     | language\_id | The id of the language  |

#### addNewUser ####

Method: _public Long addNewUser(String SID, String username, String userpass, String lastname, String firstname, String email, String  additionalname, String street, String zip, String fax, long states\_id, String town, long language\_id, String baseURL)_


Description: Adds a new Usre like through the Frontend, but also does activates the Account

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | userpass | any userpass    |
| String   | lastname | any lastname    |
| String   | firstname | any firstname   |
| String   | email    | any email       |
| String   | additionalname | any additionalname |
| String   | street   | any street      |
| String   | zip      | any zip         |
| String   | fax      | any fax         |
| String   | states\_id | a valid states\_id |
| String   | town     | any town        |
| String   | language\_id | the language\_id |
| String   | baseURL  | the baseURL is needed to send the Initial Email correctly to that User, otherwise the Link in the EMail that the new User will reveive is not valid |


#### addNewUserWithTimeZone ####

Method: _public Long addNewUserWithTimeZone(String SID, String username, String userpass, String lastname, String firstname, String email, String additionalname, String street, String zip, String fax,
> long states\_id, String town, long language\_id, String baseURL, String jNameTimeZone)_


Description: Adds a new Usre like through the Frontend, but also does activates the Account

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | userpass | any userpass    |
| String   | lastname | any lastname    |
| String   | firstname | any firstname   |
| String   | email    | any email       |
| String   | additionalname | any additionalname |
| String   | street   | any street      |
| String   | zip      | any zip         |
| String   | fax      | any fax         |
| String   | states\_id | a valid states\_id |
| String   | town     | any town        |
| String   | language\_id | the language\_id |
| String   | baseURL  | the baseURL is needed to send the Initial Email correctly to that User, otherwise the Link in the EMail that the new User will reveive is not valid |
| String   | jNameTimeZone | the name of the timezone for the user |


#### addNewUserWithExternalType ####

Method: _public Long addNewUserWithExternalType(String SID, String username, String userpass, String lastname, String firstname, String email, String additionalname, String street, String zip, String fax, long states\_id, String town, long language\_id, String baseURL, String jNameTimeZone, Long externalUserId, String externalUserType)_

Description: Adds a new Usre like through the Frontend, but also does activates the Account, sends NO email (no matter what you configured) and sets the users external user id and type

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | userpass | any userpass    |
| String   | lastname | any lastname    |
| String   | firstname | any firstname   |
| String   | email    | any email       |
| String   | additionalname | any additionalname |
| String   | street   | any street      |
| String   | zip      | any zip         |
| String   | fax      | any fax         |
| String   | states\_id | a valid states\_id |
| String   | town     | any town        |
| String   | language\_id | the language\_id |
| String   | jNameTimeZone | the name of the timezone for the user |
| Long     | externalUserId | externalUserId  |
| String   | externalUserType | externalUserType |

#### deleteUserById ####

Method: _public Long deleteUserById(String SID, Long userId)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| Long     | userId   | userId          |

#### deleteUserByExternalUserIdAndType ####

Method: _public Long deleteUserByExternalUserIdAndType(String SID, Long externalUserId, String externalUserType)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| Long     | externalUserId | externalUserId  |
| String   | externalUserType | externalUserType |

#### setUserObject ####

Method: _public Long setUserObject(String SID, String username, String firstname, String lastname, String profilePictureUrl, String email)_

_deprecated use setUserObjectAndGenerateRoomHash_

Description: sets the SessionObject for a certain SID, after setting this Session-Object you can use the
SID + a RoomId to enter any Room.

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| String   | profilePictureUrl | any profilePictureUrl |
| String   | email    | any email       |


#### setUserObjectAndGenerateRoomHash ####

Method: _public Long setUserObjectAndGenerateRoomHash(String SID, String username, String firstname, String lastname,String profilePictureUrl, String email, Long externalUserId, String externalUserType, Long room\_id, int becomeModeratorAsInt, int showAudioVideoTestAsInt)_

Description: sets the SessionObject for a certain SID, after setting this Session-Object you can use the
SID + a RoomId to enter any Room. ... Session-Hashs are deleted 15 minutes after the creation if not used.

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| String   | profilePictureUrl | any profilePictureUrl |
| String   | email    | any email       |
| Number   | externalUserId | if you have any external user Id you may set it here |
| String   | externalUserType | you can specify your system-name here, for example "moodle" |
| Number   | room\_id | the room id the user should be logged in |
| Number   | becomeModeratorAsInt | 0 means no Moderator, 1 means Moderator |
| Number   | showAudioVideoTestAsInt | 0 means don't show Audio/Video Test, 1 means show Audio/Video Test Application before the user is logged into the room |

#### setUserObjectAndGenerateRoomHashByURL ####

Method: _public Long setUserObjectAndGenerateRoomHashByURL(String SID, String username, String firstname, String lastname,String profilePictureUrl, String email, Long externalUserId, String externalUserType, Long room\_id, int becomeModeratorAsInt, int showAudioVideoTestAsInt)_

Description: sets the SessionObject for a certain SID, after setting this Session-Object you can use the
SID + a RoomId to enter any Room.

++ the user can press f5 to reload the page / use the link several times, the SOAP Gateway does remember the IP of the user and the will only the first user that enters the room allow to re-enter. ... Session-Hashs are deleted 15 minutes after the creation if not used.

available since [Issue 1016](https://code.google.com/p/openmeetings/issues/detail?id=1016)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| String   | profilePictureUrl | any profilePictureUrl |
| String   | email    | any email       |
| Number   | externalUserId | if you have any external user Id you may set it here |
| String   | externalUserType | you can specify your system-name here, for example "moodle" |
| Number   | room\_id | the room id the user should be logged in |
| Number   | becomeModeratorAsInt | 0 means no Moderator, 1 means Moderator |
| Number   | showAudioVideoTestAsInt | 0 means don't show Audio/Video Test, 1 means show Audio/Video Test Application before the user is logged into the room |


#### setUserObjectMainLandingZone ####

Method: _public String setUserObjectMainLandingZone(String SID, String username, String firstname, String lastname, String profilePictureUrl, String email, Long externalUserId, String externalUserType)_

Description: sets the SessionObject for a certain SID, after setting this Session-Object you can use the SID and directly login into the dashboard

++ the user can press f5 to reload the page / use the link several times, the SOAP Gateway does remember the IP of the user and the will only the first user that enters the room allow to re-enter. ... Session-Hashs are deleted 15 minutes after the creation if not used.

available since [r3530](https://code.google.com/p/openmeetings/source/detail?r=3530)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| String   | profilePictureUrl | any absolute profilePictureUrl |
| String   | email    | any email       |
| Number   | externalUserId | if you have any external user Id you may set it here |
| String   | externalUserType | you can specify your system-name here, for example "moodle" |

#### setUserAndNickName ####

Method: _public String setUserAndNickName(String SID, String username, String firstname, String lastname, String profilePictureUrl, String email, Long externalUserId, String externalUserType, Long room\_id, int becomeModeratorAsInt, int showAudioVideoTestAsInt, int showNickNameDialogAsInt)_

Description: sets the SessionObject for a certain SID, after setting this Session-Object you can use the
SID + a RoomId to enter any Room.

++ the user can press f5 to reload the page / use the link several times, the SOAP Gateway does remember the IP of the user and the will only the first user that enters the room allow to re-enter. ... Session-Hashs are deleted 15 minutes after the creation if not used.

++ Additionally you can set a param **showNickNameDialogAsInt**, the effect if that param is 1 is, that the user gets a popup where he can enter his nickname right before he enters the conference room. All nicknames and emails users enter are logged in the conferencelog table.

available since [r3520](https://code.google.com/p/openmeetings/source/detail?r=3520)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| String   | profilePictureUrl | any profilePictureUrl |
| String   | email    | any email       |
| Number   | externalUserId | if you have any external user Id you may set it here |
| String   | externalUserType | you can specify your system-name here, for example "moodle" |
| Number   | room\_id | the room id the user should be logged in |
| Number   | becomeModeratorAsInt | 0 means no Moderator, 1 means Moderator |
| Number   | showAudioVideoTestAsInt | 0 means don't show Audio/Video Test, 1 means show Audio/Video Test Application before the user is logged into the room |
| Number   | showNickNameDialogAsInt | 0 means do not show the popup to enter a nichname, 1 means that there is a popup to enter the nickname for the conference |

#### setUserObjectAndGenerateRecordingHashByURL ####

_public String setUserObjectAndGenerateRecordingHashByURL(String SID, String username, String firstname, String lastname, Long externalUserId, String externalUserType, Long recording\_id)_

Description: Use this method to access a Recording instead of Room

available since OpenMeetings 1.1

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID from getSession |
| String   | username | any username    |
| String   | firstname | any firstname   |
| String   | lastname | any lastname    |
| Number   | externalUserId | if you have any external user Id you may set it here |
| String   | externalUserType | you can specify your system-name here, for example "moodle" |
| Number   | recording\_id | the id of the recording, get a List of all Recordings with RoomService :: getFlvRecordingByExternalRoomType |


---


### FileService ###

Service-Endpoint:
http://localhost:5080/openmeetings/services/FileService?wsdl
(http://$RED5_HOST:$RED5_HTTP_HOST/openmeetings/services/FileService?wsdl)


#### importFile ####

Method: _public FileImportError[.md](.md) importFile(String SID, Long externalUserId, Long externalFileId, String externalType, Long room\_id, boolean isOwner, String path, Long parentFolderId, String fileSystemName)_

Description: Import file from external source
to upload a file to a room-drive you specify:
  * externalUserId, user if of openmeetings user for which we upload the file
  * externalType random string (should be the same for users from the same external system for example "moodle" or "sugar" cause there could be the same user-id used in both systems)
  * room\_id = openmeetings room id
  * isOwner = false
  * parentFolderId = 0

to upload a file to a private-drive you specify:
  * externalUserId, user if of openmeetings user for which we upload the file
  * externalType random string (should be the same for users from the same external system for example "moodle" or "sugar" cause there could be the same user-id used in both systems)
  * room\_id = openmeetings room id
  * isOwner = true
  * parentFolderId = -2

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | externalUserId | the external user id => If the file should goto a private section of any user, this number needs to be set |
| Long     | externalFileId | the external file-type to identify the file later |
| String   | externalType | the name of the external system |
| Long     | room\_id | the room Id, if the file goes to the private folder of an user, you can set a random number here |
| Boolean  | isOwner  | specify a 1/true AND parentFolderId==-2 to make the file goto the private section |
| String   | path     | http-path where we can grab the file from, the file has to be accessible from the OpenMeetings server |
| Long     | parentFolderId | specify a parentFolderId==-2 AND isOwner == 1/true AND to make the file goto the private section |
| String   | fileSystemName | the filename => Important WITH file extension! |



#### importFileByInternalUserId ####

Method: _public FileImportError[.md](.md) importFileByInternalUserId(String SID, Long internalUserId, Long externalFileId, String externalType, Long room\_id, boolean isOwner, String path, Long parentFolderId, String fileSystemName)_

Description: Import file from external source
to upload a file to a room-drive you specify:
  * internalUserId, user if of openmeetings user for which we upload the file
  * room\_id = openmeetings room id
  * isOwner = false
  * parentFolderId = 0

to upload a file to a private-drive you specify:
  * internalUserId, user if of openmeetings user for which we upload the file
  * room\_id = openmeetings room id
  * isOwner = true
  * parentFolderId = -2

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | internalUserId | the openmeetings user id => If the file should goto a private section of any user, this number needs to be set |
| Long     | externalFileId | the external file-type to identify the file later |
| String   | externalType | the name of the external system |
| Long     | room\_id | the room Id, if the file goes to the private folder of an user, you can set a random number here |
| Boolean  | isOwner  | specify a 1/true AND parentFolderId==-2 to make the file goto the private section |
| String   | path     | http-path where we can grab the file from, the file has to be accessible from the OpenMeetings server |
| Long     | parentFolderId | specify a parentFolderId==-2 AND isOwner == 1/true AND to make the file goto the private section |
| String   | fileSystemName | the filename => Important WITH file extension! |


---


#### addFolderByExternalUserIdAndType ####

Method: _public Long addFolderByExternalUserIdAndType(String SID, Long externalUserId, String externalUserType, Long parentFileExplorerItemId, String folderName, Long room\_id, Boolean isOwner, Long externalFilesid, String externalType)_

Description: Import file from external source
to upload a file to a room-drive you specify:
  * externalUserId, user if of openmeetings user for which we upload the file
  * externalType random string (should be the same for users from the same external system for example "moodle" or "sugar" cause there could be the same user-id used in both systems)
  * room\_id = openmeetings room id
  * isOwner = false
  * parentFolderId = 0

to upload a file to a private-drive you specify:
  * externalUserId, user if of openmeetings user for which we upload the file
  * room\_id = openmeetings room id
  * isOwner = true
  * parentFolderId = -2

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | externalUserId | externalUserId  |
| Long     | parentFileExplorerItemId | Id of parent folder |
| String   | folderName | folder name     |
| Long     | room\_id | Room id         |
| Boolean  | isOwner  | isOwner         |
| Long     | externalFilesid | externalFilesid |
| String   | externalType | externalType    |



---


#### addFolderByUserId ####

Method: _public Long addFolderByUserId(String SID, Long userId, Long parentFileExplorerItemId, String folderName, Long room\_id, Boolean isOwner, Long externalFilesid, String externalType)_

Description: Import file from external source
to upload a file to a room-drive you specify:
  * userId, openmeetings user for which we upload the file
  * room\_id = openmeetings room id
  * isOwner = false
  * parentFolderId = 0

to upload a file to a private-drive you specify:
  * userId, openmeetings user for which we upload the file
  * room\_id = openmeetings room id
  * isOwner = true
  * parentFolderId = -2

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | userId   | userId          |
| Long     | parentFileExplorerItemId | Id of parent folder |
| String   | fileName | file or fodler name |
| Long     | room\_id | Room id         |
| Boolean  | isOwner  | isOwner         |
| Long     | externalFilesid | externalFilesid |
| String   | externalType | externalType    |


---


#### deleteFileOrFolderByExternalIdAndType ####

Method: _public Long deleteFileOrFolderByExternalIdAndType(String SID, Long externalFilesid, String externalType)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | externalFilesid | external files id |
| String   | externalType | externalType    |


---


#### getPresentationPreviewFileExplorer ####

Method: _public LibraryPresentation getPresentationPreviewFileExplorer(String SID, String parentFolder)_

Returns an object that contains a description about the document and the list of the path to the available thumbnails for each page.
See also: [LibraryPresentation-Object](http://code.google.com/p/openmeetings/source/browse/trunk/singlewebapp/src/app/org/openmeetings/app/data/file/dto/LibraryPresentation.java)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | parentFolder | name of the folder/hash-name |


---


#### getImportFileExtensions ####

Method: _public String[.md](.md) getImportFileExtensions()_

Returns list of valid file extensions to import.

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|


---


#### deleteFileOrFolder ####

Method: _public Long deleteFileOrFolder(String SID, Long fileExplorerItemId)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | fileExplorerItemId | files id        |


---


### RoomService ###

Service-Endpoint:
http://localhost:5080/openmeetings/services/RoomService?wsdl
(http://$RED5_HOST:$RED5_HTTP_HOST/openmeetings/services/RoomService?wsdl)



#### deleteFlvRecording ####

Method: _public void deleteFlvRecording(String SID, Long flvRecordingId)_

Description: Deletes a flv recording

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | flvRecordingId | the id of the recording  |


---



#### getFlvRecordingByExternalUserId ####

Method: _public FLVRecordingReturn[.md](.md) getFlvRecordingByExternalUserId(String SID, String externalUserId)_

Description: Gets a list of flv recordings

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | externalUserId | the externalUserId  |


---


#### getFlvRecordingByExternalRoomTypeAndCreator ####

Method: _public FLVRecordingReturn[.md](.md) getFlvRecordingByExternalRoomTypeAndCreator(String SID, String externalRoomType, Long insertedBy)_

Description: Gets a list of flv recordings

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | externalRoomType | externalRoomType param  |
| Long     | insertedBy | the userId that created the recording |


---


#### getRoomsPublic ####

Method: _public RoomsList getRoomsPublic(String SID, Long roomtypes\_id)_

Description: Returns an Object of Type [RoomsList](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/data/basic/rooms/RoomsList.java) which contains a list of Room-Objects.
Every [Room-Object](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java) contains a Roomtype and all informations about that Room. The List of
current-users in the room is Null if you get them via SOAP.
The Roomtype can be 1 for conference rooms or 2 for audience rooms.

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | roomtypes\_id | The type of Rooms you want to get |

#### getRoomTypes ####

Method: _public List`*`<RoomTypes`*`> getRoomTypes(String SID)_

Description: Returns a List of Objects of [RoomsType](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/RoomTypes.java)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |


---


#### getRoomCounters ####


Method: _public RoomCountBean[.md](.md) getRoomCounters(String SID, Integer roomId1, Integer roomId2, Integer roomId3, Integer roomId4
> , Integer roomId5, Integer roomId6, Integer roomId7, Integer roomId8, Integer roomId9, Integer roomId10)_

Description: Returns the number of users in for the given roomIds

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Integer  | roomId1  | roomId 1        |
| Integer  | roomId2  | roomId 2        |
| Integer  | roomId3  | roomId 3        |
| Integer  | roomId4  | roomId 4        |
| Integer  | roomId5  | roomId 5        |
| Integer  | roomId6  | roomId 6        |
| Integer  | roomId7  | roomId 7        |
| Integer  | roomId8  | roomId 8        |
| Integer  | roomId9  | roomId 9        |
| Integer  | roomId10 | roomId 10       |


---


#### getRoomById ####

Method: _public Rooms getRoomById(String SID, long rooms\_id)_

Description: Returns an Object of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | rooms\_id | The Roomid you want to get |


#### getRoomWithCurrentUsersById ####

Method: _public Rooms getRoomWithCurrentUsersById(String SID, long rooms\_id)_

Description: Returns an Object of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java) It also fills the attribute **currentUsers** in the Room-Object

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | rooms\_id | The Roomid you want to get |

#### getRooms ####

Method: _public SearchResult getRooms(String SID, int start, int max, String orderby, boolean asc)_

Description: Returns a List of Objects of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)
You can use "name" as value for orderby or rooms\_id

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| int      | start    | The id you want to start |
| int      | max      | The maximum you want to get |
| String   | orderby  | The column it will be ordered |
| Boolean  | asc      | Asc or Desc sort ordering |


#### getRoomsWithCurrentUsers ####

Method: _public SearchResult getRoomsWithCurrentUsers(String SID, int start, int max, String orderby, boolean asc)_

Description: Returns a List of Objects of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)
You can use "name" as value for orderby or rooms\_id. It also fills the attribute **currentUsers** in the Room-Object

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| int      | start    | The id you want to start |
| int      | max      | The maximum you want to get |
| String   | orderby  | The column it will be ordered |
| Boolean  | asc      | Asc or Desc sort ordering |

#### addRoom ####

(deprecated use addRoomWithModeration)

Method: _public Long addRoom(String SID, .... see list of Params)_

Description: Create an Objects of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Integer  | videoPodWidth | the PodWidth    |
| Integer  | videoPodHeight | the videoPodHeight |
| Integer  | videoPodXPosition | the videoPodXPosition |
| Integer  | videoPodYPosition | the videoPodYPosition |
| Integer  | moderationPanelXPosition | the moderationPanelXPosition |
| Boolean  | showWhiteBoard | the showWhiteBoard |
| Integer  | whiteBoardPanelXPosition | the whiteBoardPanelXPosition |
| Integer  | whiteBoardPanelYPosition | the whiteBoardPanelYPosition |
| Integer  | whiteBoardPanelHeight | the whiteBoardPanelHeight |
| Integer  | whiteBoardPanelWidth | the whiteBoardPanelWidth |
| Boolean  | showFilesPanel | the showFilesPanel |
| Integer  | filesPanelXPosition | the filesPanelXPosition |
| Integer  | filesPanelYPosition | the filesPanelYPosition |
| Integer  | filesPanelHeight | the filesPanelHeight |
| Integer  | filesPanelWidth | the filesPanelWidth |


#### addRoomWithModeration ####

Method: _public Long addRoomWithModeration(String SID, .... see list of Params)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |

#### updateRoom ####

(deprecated use updateRoomWithModeration)

Method: _public Long updateRoom(String SID, .... see list of Params)_

Description: Update an Objects of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | rooms\_id | Roomid to update |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Integer  | videoPodWidth | the PodWidth    |
| Integer  | videoPodHeight | the videoPodHeight |
| Integer  | videoPodXPosition | the videoPodXPosition |
| Integer  | videoPodYPosition | the videoPodYPosition |
| Integer  | moderationPanelXPosition | the moderationPanelXPosition |
| Boolean  | showWhiteBoard | the showWhiteBoard |
| Integer  | whiteBoardPanelXPosition | the whiteBoardPanelXPosition |
| Integer  | whiteBoardPanelYPosition | the whiteBoardPanelYPosition |
| Integer  | whiteBoardPanelHeight | the whiteBoardPanelHeight |
| Integer  | whiteBoardPanelWidth | the whiteBoardPanelWidth |
| Boolean  | showFilesPanel | the showFilesPanel |
| Integer  | filesPanelXPosition | the filesPanelXPosition |
| Integer  | filesPanelYPosition | the filesPanelYPosition |
| Integer  | filesPanelHeight | the filesPanelHeight |
| Integer  | filesPanelWidth | the filesPanelWidth |


#### updateRoomWithModeration ####

Method: _public Long updateRoomWithModeration(String SID, .... see list of Params)_


... List of Params tbd ...


#### deleteRoom ####

Method: _public Long deleteRoom(String SID, long rooms\_id)_

Description: Delete an Objects of [Rooms](http://openmeetings.googlecode.com/svn/trunk/webapp/src/app/org/openmeetings/app/hibernate/beans/rooms/Rooms.java)


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | rooms\_id | Roomid to delete |


#### kickUser ####

Method: _public Boolean kickUser(String SID\_Admin, Long room\_id)_

Description: Removes all Users from a Room


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID\_Admin |a admin user's SessionId |
| Long     | rooms\_id | the room's id of the room to get empty |


#### getRoomIdByExternalId ####

Method: _public Long getRoomIdByExternalId(String SID, .... see list of Params)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| Long     | externalRoomId | your external room id may set here |
| String   | externalRoomType | you can specify your system-name or type of room here, for example "moodle" |


---


#### addRoomWithModerationAndQuestions ####

Method: _public Long addRoomWithModerationAndQuestions(String SID, String name,
> Long roomtypes\_id ,
> String comment, Long numberOfPartizipants,
> Boolean ispublic,
> Boolean appointment,
> Boolean isDemoRoom,
> Integer demoTime,
> Boolean isModeratedRoom,
> Boolean allowUserQuestions)_

this SOAP Method has an additional param to enable or disable the
buttons to apply for moderation
this does only work in combination with the room-type restricted

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |


---


#### addRoomWithModerationAndQuestions ####

Method: _public Long updateRoomWithModerationAndQuestions(String SID,
> Long room\_id,
> String name,
> Long roomtypes\_id ,
> String comment, Long numberOfPartizipants,
> Boolean ispublic,
> Boolean appointment,
> Boolean isDemoRoom,
> Integer demoTime,
> Boolean isModeratedRoom,
> Boolean allowUserQuestions)_

this SOAP Method has an additional param to enable or disable the
buttons to apply for moderation
this does only work in combination with the room-type restricted

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | room\_id | Room-Id         |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |


---


#### addRoomWithModerationQuestionsAndAudioType ####

Method: _public Long addRoomWithModerationQuestionsAndAudioType(String SID, String name,
> Long roomtypes\_id ,
> String comment, Long numberOfPartizipants,
> Boolean ispublic,
> Boolean appointment,
> Boolean isDemoRoom,
> Integer demoTime,
> Boolean isModeratedRoom,
> Boolean allowUserQuestions,
> Boolean isAudioOnly)_

this SOAP Method has an additional param to enable or disable the
video

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |
| Boolean  | isAudioOnly | enable or disable the video/audio-only |


---


#### addRoomWithModerationAndExternalType ####

Method: _public Long addRoomWithModerationAndExternalType(String SID, String name,
> Long roomtypes\_id, String comment, Long numberOfPartizipants,
> Boolean ispublic, Boolean appointment, Boolean isDemoRoom,
> Integer demoTime, Boolean isModeratedRoom, String externalRoomType)_


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| Boolean  | externalRoomType| externalRoomType |


---


#### addRoomWithModerationExternalTypeAndAudioType ####

Method: _public Long addRoomWithModerationExternalTypeAndAudioType(String SID, String name,
> Long roomtypes\_id, String comment, Long numberOfPartizipants,
> Boolean ispublic, Boolean appointment, Boolean isDemoRoom,
> Integer demoTime, Boolean isModeratedRoom, String externalRoomType,
> Boolean allowUserQuestions, Boolean isAudioOnly)_


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| String   | externalRoomType | externalRoomType |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |
| Boolean  | isAudioOnly | enable or disable the video/audio-only |


---


#### addRoomWithModerationAndRecordingFlags ####

Method: _public Long addRoomWithModerationAndRecordingFlags(String SID, String name,
> Long roomtypes\_id, String comment, Long numberOfPartizipants,
> Boolean ispublic, Boolean appointment, Boolean isDemoRoom,
> Integer demoTime, Boolean isModeratedRoom, String externalRoomType,
> Boolean allowUserQuestions, Boolean isAudioOnly,
> Boolean waitForRecording, Boolean allowRecording)_


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| String   | externalRoomType | externalRoomType |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |
| Boolean  | isAudioOnly | enable or disable the video/audio-only |
| Boolean  | waitForRecording | waitForRecording PopUp |
| Boolean  | allowRecording | allowRecording Flag |


---


#### addRoomWithModerationExternalTypeAndTopBarOption ####

Method: _public Long addRoomWithModerationExternalTypeAndTopBarOption(String SID, String name,
> Long roomtypes\_id, String comment, Long numberOfPartizipants,
> Boolean ispublic, Boolean appointment, Boolean isDemoRoom,
> Integer demoTime, Boolean isModeratedRoom, String externalRoomType,
> Boolean allowUserQuestions, Boolean isAudioOnly,
> Boolean waitForRecording, Boolean allowRecording,
> Boolean hideTopBar)_


Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| String   | externalRoomType | externalRoomType |
| Boolean  | allowUserQuestions | enable or disable the button to allow users to apply for moderation |
| Boolean  | isAudioOnly | enable or disable the video/audio-only |
| Boolean  | waitForRecording | waitForRecording PopUp |
| Boolean  | allowRecording | allowRecording Flag |
| Boolean  | hideTopBar | hideTopBar Flag |


---


#### getFlvRecordingByExternalRoomType ####

Method: _public FlvRecording[.md](.md) getFlvRecordingByExternalRoomType(String SID, String externalRoomType)_

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | externalRoomType | External Type of Room |

#### getInvitationHash ####

Method: _public String getInvitationHash(String SID, String username, Long room\_id, Boolean isPasswordProtected, String invitationpass, Integer valid, String validFromDate,
String validFromTime, String validToDate, String validToTime)_


Create a Invitation hash
the From to Date is as String as some SOAP libraries do not accept
Date Objects in SOAP Calls Date is parsed as dd.mm.yyyy, time as hh:mm (don't forget the leading zero's)

Returns a HASH value that can be made into a URL with http://$OPENMEETINGS_HOST:$PORT/openmeetings/?invitationHash=$invitationsHash;

available since [Issue 1154](https://code.google.com/p/openmeetings/issues/detail?id=1154)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | username | the username of the User that he will get |
| Long     | room\_id | the username of the User that he will get |
| Boolean  | isPasswordProtected | if the invitation is password protected |
| String   | invitationpass | the password for accessing the conference room via the invitation hash |
| Integer  | valid    | the type of validation for the hash 1: endless, 2: from-to period, 3: one-time |
| String   | validFromDate | Date in Format of dd.mm.yyyy only of interest if valid is type 2 |
| String   | validFromTime | time in Format of hh:mm only of interest if valid is type 2 |
| String   | validToDate | Date in Format of dd.mm.yyyy only of interest if valid is type 2 |
| String   | validToTime | time in Format of hh:mm only of interest if valid is type 2 |




#### sendInvitationHash ####

Method: _public String sendInvitationHash(String SID, String username, String message,  String baseurl, String email, String subject, Long room\_id, String conferencedomain, Boolean isPasswordProtected, String invitationpass, Integer valid, String validFromDate, String validFromTime, String validToDate, String validToTime,Long language\_id, Boolean sendMail)_


Create a Invitation hash  and optionally send it by mail
the From- and To- Date's are Strings as some SOAP libraries do not accept
Date Objects in SOAP Calls. Those Dates are parsed as dd.mm.yyyy, time-strings as hh:mm (don't forget the leading zero's)

Returns a HASH value that can be made into a URL with http://$OPENMEETINGS_HOST:$PORT/openmeetings/?invitationHash=$invitationsHash;

available since [Issue 1154](https://code.google.com/p/openmeetings/issues/detail?id=1154)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | username | the username of the User that he will get |
| String   | message  | the Message in the Email Body send with the invitation if sendMail is true |
| String   | baseurl  | the baseURL for the Infivations link in the Mail Body if sendMail is true |
| String   | email    | the Email to send the invitation to if sendMail is true |
| String   | subject  | the subject of the Email send with the invitation if sendMail is true |
| Long     | room\_id | the username of the User that he will get |
| String   | conferencedomain | the domain of the room (keep empty not in use at the moment) |
| Boolean  | isPasswordProtected | if the invitation is password protected |
| String   | invitationpass | the password for accessing the conference room via the invitation hash |
| Integer  | valid    | the type of validation for the hash 1: endless, 2: from-to period, 3: one-time |
| String   | validFromDate | Date in Format of dd.mm.yyyy only of interest if valid is type 2 |
| String   | validFromTime | time in Format of hh:mm only of interest if valid is type 2 |
| String   | validToDate | Date in Format of dd.mm.yyyy only of interest if valid is type 2 |
| String   | validToTime | time in Format of hh:mm only of interest if valid is type 2 |
| Long     | language\_id | the language id of the EMail that is send with the invitation if sendMail is true |
| Boolean  | sendMail | if sendMail is true then the RPC-Call will send the invitation to the email |



#### sendInvitationHashWithDateObject ####

Method: _public String sendInvitationHash(String SID, String username, String message,  String baseurl, String email, String subject, Long room\_id, String conferencedomain, Boolean isPasswordProtected, String invitationpass, Integer valid, Date fromDate, Date toDate, Long language\_id, Boolean sendMail)_


Create a Invitation hash  and optionally send it by mail
Date Objects in SOAP Calls Date are expected to be full Date object. So you can set Hours, Minutes and Seconds here directly in the Date Object.

Returns a HASH value that can be made into a URL with http://$OPENMEETINGS_HOST:$PORT/openmeetings/?invitationHash=$invitationsHash;

available since [Issue 1154](https://code.google.com/p/openmeetings/issues/detail?id=1154)

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | username | the username of the User that he will get |
| String   | message  | the Message in the Email Body send with the invitation if sendMail is true |
| String   | baseurl  | the baseURL for the Infivations link in the Mail Body if sendMail is true |
| String   | email    | the Email to send the invitation to if sendMail is true |
| String   | subject  | the subject of the Email send with the invitation if sendMail is true |
| Long     | room\_id | the username of the User that he will get |
| String   | conferencedomain | the domain of the room (keep empty not in use at the moment) |
| Boolean  | isPasswordProtected | if the invitation is password protected |
| String   | invitationpass | the password for accessing the conference room via the invitation hash |
| Integer  | valid    | the type of validation for the hash 1: endless, 2: from-to period, 3: one-time |
| Date     | fromDate | Date Object     |
| Date     | toDate   | Date Object     |
| Long     | language\_id | the language id of the EMail that is send with the invitation if sendMail is true |
| Boolean  | sendMail | if sendMail is true then the RPC-Call will send the invitation to the email |


#### getRoomsWithCurrentUsersByList ####

Method: _public List <RoomReturn> getRoomsWithCurrentUsersByList(String SID, int start, int max, String orderby, boolean asc)_


Returns a List of RoomReturn Objects that contains all current users with their Session attributes.

available since version 1.4

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| int      | start    | the first id of the Rooms that should be returned |
| int      | max      | the number of objects in the list |
| String   | orderby  | the attribute it should be ordered by (for example rooms\_id) |
| boolean  | asc      | the sort order  |


#### getRoomsWithCurrentUsersByListAndType ####

Method: _public List <RoomReturn> getRoomsWithCurrentUsersByListAndType(String SID, int start, int max, String orderby, boolean asc, String externalRoomType)_

Returns a List of RoomReturn Objects that contains all current users with their Session attributes. But only the ones of a certain externalRoomType

available since version 1.4

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| int      | start    | the first id of the Rooms that should be returned |
| int      | max      | the number of objects in the list |
| String   | orderby  | the attribute it should be ordered by (for example rooms\_id) |
| boolean  | asc      | the sort order  |
| String   | externalRoomType | the name of the external room type to return |


#### addRoomWithModerationAndExternalTypeAndStartEnd ####

Method: _public Long addRoomWithModerationAndExternalTypeAndStartEnd(String SID, String name,
> Long roomtypes\_id, String comment, Long numberOfPartizipants,
> Boolean ispublic, Boolean appointment, Boolean isDemoRoom,
> Integer demoTime, Boolean isModeratedRoom, String externalRoomType,
> String validFromDate,
> String validFromTime,
> String validToDate,
> String validToTime,
> Boolean isPasswordProtected,
> String password,
> Long reminderTypeId,
> String redirectURL
> )_

Add a Room and link it to the Calendar with a start and end date

available since version 1.4

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| String   | name     | Name of the Room |
| Long     | roomtypes\_id | Type of that room (1 = Conference, 2 = Audience, 3 = Restricted, 4 = Interview) |
| String   | comment  | any comment     |
| Long     | numberOfPartizipants | the maximum users allowed in this room |
| Boolean  | ispublic | If this room is public (use true if you don't deal with different Organizations) |
| Boolean  | appointment | is it a Calendar Room (use false by default) |
| Boolean  | isDemoRoom | is it a Demo Room with limited time (use false by default) |
| Boolean  | demoTime | time in seconds after the user will be logged out (only enabled if isDemoRoom is true)|
| Boolean  | isModeratedRoom | Users have to wait untill a Moderator arrives. Use the becomeModerator param in setUserObjectAndGenerateRoomHash to set a user as default Moderator |
| String   | externalRoomType | name of the type of external room, for example "Moodle" |
| String   | validFromDate | Date, Format: dd.MM.yyyy |
| String   | validFromTime | Date, Format: mm:hh |
| String   | validToDate | Date, Format: dd.MM.yyyy |
| String   | validToTime | Date, Format: mm:hh |
| Boolean  | isPasswordProtected | If the links send via EMail to invited people is password protected |
| String   | password | Password for Invitations send via Mail |
| Long     | reminderTypeId | 1=none, 2=simple mail, 3=ICAL |
| Long     | redirectURL | URL Users will be lead to if the Conference Time is elapsed |



#### addMeetingMemberRemindToRoom ####

Method: _public Long addMeetingMemberRemindToRoom(String SID, Long room\_id, String firstname, String lastname, String email, String baseUrl, Long language\_id)_

Add a meeting member to a certain room. This is the same as adding an external user to a event in the calendar.

available since version 1.4

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | room\_id | The Room Id the meeting member is going to be added |
| String   | firstname | The firstname of the meeting member |
| String   | lastname | The lastname of the meeting member |
| String   | email    | The email of the Meeting member |
| String   | baseUrl  | The baseUrl, this is important to send the correct link in the invitation to the meeting member |
| Long     | language\_id | The ID of the language, for the email that is send to the meeting member |



#### closeRoom ####

Method: _public int closeRoom(String SID, Long room\_id, Boolean status)_

Method to remotely close or open rooms. If a room is closed all users inside the room and all users that try to enter it will be redirected to the **redirectURL** that is defined in the Room-Object.

Returns positiv value if authentification was successful.

available since version 1.4

Params:
| **type** | **name** | **description** |
|:---------|:---------|:----------------|
| String   | SID      | The SID of the User. This SID must be marked as Loggedin |
| Long     | room\_id | The Room Id to be closed or opened |
| Boolean  | status   | false = close, true = open |


