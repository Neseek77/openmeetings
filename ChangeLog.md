## Changes in OpenMeetings 1.9.1 (released on Tueasday 13.12.2011) ##

  * Fixes JAR conflict that leads to startup problems on some machines (javax.persistence.`*` Exception)

## Changes in OpenMeetings 1.9.0 (released on Tuesday 05.12.2011) ##

  * Run OpenMeetings over SSL using HTTPS and RTMPS see [RTMPSandHTTPS](RTMPSandHTTPS.md)
  * Rename the complete application webapp path see [CustomizingWebappName](CustomizingWebappName.md)
  * rtmpT (rtmp over HTTP Tunneling) working again
  * Save polls: You can now save polls, delete polls and close polls. Also poll results are displayed as pie-charts.
  * Mute any user: You can mute yourself. Additionally moderators can also mute other microphones. Mute status is displayed in user-list and status icons in video pod and on top of the user list
  * Give exclusive microphone: Mute everybody except yourself with a single click. The User can click on his video pod (or press F12), if he has the needed rights, his micro is unmuted while everybody else is muted. The moderator can additionally give the right to have the functionality to "Give exclusive microphone" to to any user and/or can also directly click on any other participant's video to "Give exclusive microphone" to this user.
  * You can now see in your own video pod not only the green light but also the full activity meter indicating your loudness
  * You can now choose between multiple resolutions for the video, remote video pods are synced to the size too. With different dimension (4:3, 16:9). Does also reduce needed bandwidth to use exactly the supported resolution and you can choose smaller video sizes to save bandwidth. You can also change the available resolutions and default selection in the config.xml see ScreenResolutions
  * Invalid invitation hashs now show in the error message why and in which time period they are exactly valid
  * Refactored calendar server side code to use java.util.Timezone everywhere. The application now stores all times in the server time. Only the backup contains timestamps so you could potentially export in one time zone and import into a server of another without issues.
  * Changed calendar E-Mail and fixed iCal invitation in Gmail to display correctly
  * Calendar meeting reminder email is now send 15 minutes upfront (15 minutes can be changed in configuration)
  * Default room size for conference rooms created via calendar is now 50 (default room size can be changed in configuration)
  * Minimum length for user and password is now 4 (minimum length can be changed in configuration)
  * Fix new FFMPEG param style (you can revert to use the old param style through the config\_key "use.old.style.ffmpeg.map.option") see [SwitchBetweenFFMPEG](SwitchBetweenFFMPEG.md)
  * Max upload size can be configured now (max\_upload\_size)
  * Fix calendar if first-day-in-week is configured to be Monday instead of Sunday (default)
  * JUnit tests now run integrated in Eclipse and on the Nightly Build Server see [JUnitTestings](JUnitTestings.md)
  * Apache RAT is integrated to handle some parts of the code audit when importing our code to Apache Incubator SVN
  * Oracle available as DB configuration see Configuration\_Oracle

## Changes in OpenMeetings SugarCRM Plugin 1.2 (released on Monday 21.11.2011) ##

  * changed workflow
  * easier installation (no manualy changes needet any more)
  * easier use
  * Smarty Template Engine
  * ready for Sugarcrm 6

## Changes in OpenMeetings 1.8.8 (released on Monday 14.11.2011) ##

  * Fixes problem when participants draw simultaneously to whiteboard (In some use-cases where one participants holds the mouse-down where another user does serveral mouse-up+down actions the resulting drawing was incorrect)

## Changes in OpenMeetings 1.8.7 (released on Sunday 06.11.2011) ##

  * Fixes ENTER-Key/Event in Remote Desktop Control

## Changes in OpenMeetings 1.8.6 (released on Sunday 30.10.2011) ##

  * Refactor scheduler that sends emails to participants of calendar events
  * add option poly2bitmap to workaround issue of SWFTools "only supports 65536 shapes at once" error message

## Changes in OpenMeetings 1.8.5 (released on Saturday 22.10.2011) ##

  * Update of installer and new option to exclude the default rooms in the installation (this can be handy if you import a backup directly after installation)
  * Fix backup import tool issue with wrong organization\_id after import (Users could get assigned to a previous existing organization if there are multiple)

## Changes in OpenMeetings Moodle Mod 1.3 (released on Wednesday  19.10.2011) ##

  * Fix UTF8 problem
  * Add Russian language to Moodle Plugin room-configuration form

## Changes in OpenMeetings 1.8.4 ##

  * Zero conf installation, 3 steps to install, see InstallationOpenMeetings
  * add configuration and documentation for IBM DB2, see [Configuration\_IBM\_DB2](Configuration_IBM_DB2.md)

## Major changes in OpenMeetings Moodle Mod 1.2 (released on Tuesday  04.10.2011) ##

  * Secure "SOAP-Only" user access level
  * 3 Moderation Types for Conference Rooms (Moodle admins, teachers and course creators are automatically a moderator):
    * Moderation Type A) Participants need to wait till the teacher enters the room
    * Moderation Type B) Participants can already start (first User in room becomes moderator)
    * Moderation Type C) Every participant is automatically moderator when he/she enters the room
  * New Flag "Allow Recording": You can set in the room configuration now a flag that controls if the recording button is available or not
  * Fix Icon in Moodle Course overview and edit page
  * Fix Issue when deleting a conference room from a course (all conference rooms where deleted from all courses)
  * Add/Fix Moodle Mod languages: German, French, Spanish (OpenMeetings itself has around 30 languages)
  * Clear and restructure conference creation form for Moodle moderators

You can try and test the Moodle integration at:

http://moodle.openmeetings.de

## Changes in Version 1.8.3 (released Sunday 02.10.2011) ##

  * Fix Issue with recording flag and SOAP API

## Changes in Version 1.8.2 (released Monday 24.09.2011) ##

  * Improves language handling: The default language is bound now to the user-profile, not to the server settings!
  * Fix some issue in creating conferences via the integrated calendar
  * Fixes issues when drag and droping a video from the file-explorer to the whiteboard
  * Update Chinese Simplified

## Changes in Version 1.8.1 (released Monday 19.09.2011) ##

  * Fix Spring/openJPA migration issues in Language-Editor import and in LDAP / ADS integration module

## Major changes in Version 1.8 (released Wednesday 14.09.2011) ##

  * Updated Red5 to Red5 1.0 RC1
  * Completed refactoring and replacment of persistance layer from Hibernate with openJPA, **the hibernate.cfg.xml does not exist anymore, it is now located in WEB-INF/classes/META-INF/persistance.xml**
  * Tighter integration with Spring, fixing DB-Session Issues, also having better control about configuration
  * New feature **Activity-List**: Instead of having annoying pop-ups about requests from your students you have now a list with the chronolgical order of the requests. And you can also directly answer those requests in that list.
  * New feature **Math-Symbols**: You have now a lot more icons/graphs that you can use. There are around 2000 graphic smybols that can be used. Together with the possibility to rotate, drag and resize them you should be able to bring your formular more easily to the whiteboard
  * Lots of small improvements in the UI and user-experience, see for example: http://code.google.com/p/openmeetings/issues/list?can=1&q=status%3AFixed+MileStone%3DopenJPA

## Major changes in Moodle Mod 1.2 (released Sunday 19.06.2011) ##

  * Fix languages
  * Update the record in Moodle does update the record in OpenMeetings

## Major changes in Version 1.7 (released Wednesday 15.06.2011) ##

  * Multi-Whiteboard, you can add new whiteboard instances, each whiteboard can have the full range of tools and documents inside.
  * Advanced File-Explorer, Drag and Drop Interface for managing files, including possibility to create a document tree with folders.
  * Private and Public Drive in File-Explorer. The File-Explorer has two different views, one is the **Private Drive** and the other the **Public Drive**. The Private Drive always contains the same files. Those files are only visible to the user currently loggedin. The Public Drive is bound not to the user, but to the conference room. All users in the conference room have access to the Public Drive.
  * Save whiteboards. You can save each whiteboard instance as a file. The file is located in the File-Explorer and can be drag and drop'ed and organized like any other document, image or folder.
  * New User-Level: SOAP/Web-Service only. For security reasons there is a new user-level. You can now create user-accounts that have only the ability to access via SOAP. In former versions you always need an admin account.
  * **MyRooms** section. Each user has now by default 2 personal rooms that are always accessible exclusive for that user. There are buttons to enter those rooms from the dashboard
  * VoIP-Integration. Together with our Finish partner and the Open Source Department of Kvarken region we have done a VoIP integration by using an applet for the audio part. For further information see VoIPAndSIP

## Major changes in Version 1.6.2 (released Monday 07.03.2011) ##

  * Fix for Mysql 5.5
  * Update of persistance Library to Hibernate 3.6.1
  * Update Mysql JDBC Driver to MySQL JConnector 1.5.15
  * Rename field **comment** as it seems to be a reserved word in Oracle

## Major changes in Version 1.6.1 (released on Sunday 27.02.2011) ##

  * Fix no timezone default selection bug in invitations
  * Recordings are now in the private folder of the recordings creator
  * Processed recordings have now by default the original resolution of the recording

## Major changes in OpenMeetings Moodle Mod 1.0 (released on Sunday 09.01.2011) ##

  * Compliant to Moodle 2.0

## Major changes in Version 1.6 RC1 (released Thursday 11.11.2010) ##

  * Watch the small demo about new features: http://www.youtube.com/user/sebastwagner#p/u/0/EriKb0zY0lM
  * You can login using your Facebook account now
  * Private message center, send users messages, organize them in folders, you can book conference rooms by sending private messages, the booked event is automatically in your and the participants calendar, changes to that event will be populated to all users booked to the event.
  * User contacts, you can search users, add them to your contacts
  * Share your calendar with your contacts
  * Add offerings and interest to your profile to find people with similar interest
  * Reworked Full-fit functionality, Full-fit does rescale the document on the screen to be 100% visible on all screens, no matter what screen resolution you got. So the zoom might be 60% on one pc and 79% on a participant with different screen resolution
  * Objects drawn on the whiteboard do now zoom according to the document and the zoom itself can be now between 0 and 200% of the actual pixel size
  * Encrpyted PDFs do work now
  * The backup does contain various fixes and includes the complete folder structure of the recordings + recordorded files, and all data that is generated by the new features private messages and user contacts
  * Some new timezones and fixes for the release 1.5 regarding wrong time zones in some mail notifications
  * New SOAP calls to login to the dashboard and to show a popup to choose a custom nickname when you go directly to a conference room

## Major changes in Version 1.5 RC1 (released Sunday 26.09.2010) ##

  * Timezone support in User Profile, Calendar and Invitations. Users can now organize Events with people from different timezones and the system will keep track on handling the event time correctly, see also TimeZoneHandling
  * Re-Styling and fixing some layout aspects
  * Quick-link button next to each user-name in dashboard user list and in every chat box to quickly guide people to a conference room
  * small Bug fixes

## Major changes in Version 1.4 RC1 (released Wednesday 25.08.2010) ##

New Features:
  * Remote Desktop Control (RDC) in Screen Sharing and separated right for RDC in user list, Moderators are able by default to do RDC other users can apply for it using the right-buttons
  * New separated right for allowing Screen Sharing and Recording
  * Multi-Domain Support: Separated Admin interface for configuring multiple LDAP configurations with a single OpenMeetings Instance, in the Login Box you are able to choose different domains now, each domain represents a LDAP config (you can still use the internal DB all the time, the internal DB is always in the list)
  * New Room features: Close and open conference rooms. Additionally with a redirect-URL in case the room is closed and SOAP Methods to remotely open and close room. Moderators can also close a room from inside the conference room (Menubar: Actions > Close room). Users which are already loggedin will then be redirected to the redirect URL
  * Fix missing attributes in Backup file and add Meeting-Members
  * New Language: Danish, thx to ALO, update Spanish thx to Jesmargo
  * Various small Bugfixes

## Major changes in Moodle Mod 0.9 and SugarCRM Mod 1.1 (released on Wednesday 11.08.2010) ##

  * Fix utf8 problem with names in SOAP Gateway

## Major changes in Version 1.3 RC1 (release Wednesday 23.06.2010) ##

New Features:
  * Drawings stay on slide when you turn the page of a document on the whiteboard
  * Restricted Room Type further optimization for 100++ participants. We have done some Stress test with the University of Umeå from Sweden and Vasaa in Finland with about 120-130 participants in a Single Conference Room (Type Restricted). They went successfull. Thanks to the Team of http://www.openkvarken.fi again
  * Quality Settings in Screen Sharing Clients, contributed by Giovanni Possemato / Bitflow (http://www.bitflow.it) + RTMP-Tunneling Version of ScreenSharer fixed
  * New SOAP Calls to get User-Object List including Streamname to broadcast a Video Stream out of a Conference directly
  * Screen Sharing/Recording is now a separated right, therefore new layout for User-List in all room-types
  * Option in Room-Settings and Administration "Audio Only" so users can only do Voice Conference in those rooms
  * Video Pod hidden (Conference-Roomtype) or minimized (Webinar/Restricted-Roomtype) if User selects only Audio as device to broadcast
  * Activity-Icon highlights when user speaks, now also in User-List (Room-Type Conference) instead of Video-Pod
  * System Backup and Import - You can export now completely Users + Rooms + Organizations + all associations + all uploaded Files into a single ZIP and re-import that ZIP in a freshly installed OpenMeetings (of course you need an OpenMeetings Version greater 1.3 to be able to import the ZIP again) see also UpdateOpenMeetings

Major Bug fixes:
  * Invitations Password does not work
  * Reminder Email not beeing send before scheduled Meetings (now send 15 minutes upfront no matter if ICAL or simple Mail)
  * lots of small things

## Major changes in Version 1.2 RC1 (release Wednesday 03.03.2010) ##

  * Performance Fixes
  * New Integration Code and SOAP Methods
  * Moodle Plugin Update with performance fix
  * New Sugar CRM Plugin see [SugarCRMModule](SugarCRMModule.md) in Version 1.0

## Major changes in Version 1.1 RC1 (release Monday 08.02.2010) ##

New ScreenSharing:
  * Uses RTMP as protocol
  * Automatic switch to RTMPT
  * In that way it uses the exact same ports as Audio/Video transmission, so if you can share your cam, you can share your screen + record

New Recording Function:
  * Platform independent
  * Download recorded file as FLV or as AVI File (to watch offline, publish on your website or through Youtube)
  * Private and Public Recordings via a Drag and Drop File-Browser like interface and folders to organize recordings
  * Recording time line gives additional information who is speaking and at what time
  * Record hole screen + share screen at the the same time
  * Seek in the integrated player to any point of the recording

New Translations:
  * Greek, Dutch, Brazilian Portuguese

Major and minor Issues fixed in various areas of the Application, see:<br />
http://code.google.com/p/openmeetings/issues/list?can=1&q=milestone%3DRelease1.1

New integration features:
  * New SOAP Methods
  * New SOAP Methods + API to access Recordings directly
  * New Method to use OpenMeetings for InstantCommunication

New Moodle Plugin 0.7:
  * Updated Moodle Plugin to use new wrapper page and secure method + add Option to watch OpenMeetings Recordings directly in Moodle

Integration Advise:
  * You should change the URL to the OpenMeetings Application according to the Integration Guide to use the wrapper page

## Major changes in Version 1.0 RC1 ##

  * New third room-type: "Restricted"
  * New Calendar Module to Plan Meetings
  * New Room configuration with option to add Default Moderators and Super Moderator
  * Super Moderators (cannot be removed)
  * Simplified and reworked UI, various bugfixes and help-texts
  * New Secure Method for generating HASH's for direct Login via SOAP
  * OpenMeetings as WAR-Version
  * Various fixes and improvements
  * [Change to Eclipse Public License for OpenMeetings](http://wagner-sebastian.com/wordpress/2009/10/12/openmeetings-becomes-even-more-liberal-with-epl-license/)

## Major Changes in Moodle Mod 0.6 ##

  * Compatible with latest Nightly Builds and Version 1.0 RC1
  * Grouping and Group Configuration
  * New 3th Room-Type Restricted available via Module Form

## Major changes in Version 0.9 RC5 ##

  * New Screen Sharing Sharer and Clients using the [OpenMeetings Screen Sharing Protocol](ODSP.md), see also the [Announcement at the Mailing List](http://groups.google.com/group/openmeetings-user/browse_thread/thread/ebc77e02368a0cfc)
  * New Flexible Room Layout and Full Size Whiteboard Option
  * Various Bugfixes

## Major changes Version 0.9 RC4 ##

  * Fix error in installation and startup
  * Updated Red5 Server to red5 [revision 3823](https://code.google.com/p/openmeetings/source/detail?r=3823)

## Major changes Version 0.9 RC3 ##

  * Fix for special characters in Uploaded Images
  * Fix for special characters in Uploaded Documents
  * Fix for UTF-8 Problem when using Linux Flash Player

## Major changes in MileStone 0.9 RC2 ##

  * Multiple Moderators: A moderator can make other Users also Moderator (add or remove Moderation flag)
  * No Confirmation from all users needed anymore to get Moderator (you just send a message to the Moderator)
  * Completely refactored Event Modus with: Multiple Moderators, allow any User to draw to the whiteboard, Moderators can allow or deny any user to share his Cam or Microphone (Raise hand function)
  * New Module Configuration XML-File to runtime configure Plugins and Modules
  * New Language Translations: Swedish and Finnish contributed by www.openkvarken.fi
  * Refactored parts of Calendar to fit into Layout and design
  * Various Modifications and fixes

A complete list of all Issue working and worked on MileStone 0.9 can be seen at:

http://code.google.com/p/openmeetings/issues/list?can=1&q=milestone%3DRelease0.9

## Major changes in 0.8.4 ##

Release 0.8.4 basically adds some new SOAP Functions for directly entering and editing a Room. Some Room-Attributes have been removed that concern the Layout of a Room. However you may still use the previous SOAP Calls but it is recommended to Update to use the latest API-Calls see [SoapMethods](SoapMethods.md).

The Moodle Plugin enables you now to use more feature of OpenMeetings:
  * Create both Room Types Conference Room (Multiple Videos) and Event Room (Single Bigger Video)
  * Set an individual Room Language and Max Number of Participant per Room
  * Set a Flag for a Room so that Students and Guests have to wait and will NOT become the Moderation Role in a Conference Room by default, they will have to wait untill a Moodle-Teacher, -Admin or -Creating-Teacher comes and enters a Room.

We would like to thank all Contributors, User, Developers of OpenMeetings and [Open Kvarken](http://www.openkvarken.fi/) for supporting us.


Notes:

In the Administration I just encountered an Issue with 0.8.4:
In the Admin Panel of Rooms as => the Admin-Panel expects that there is at least a
number in the Field **demo-Time** ... so when you try to add a new Room it says
System Error. You can workaround that by enabling **Demo Room** (the checkbox)
enter a number in **demo Time** .. you can then disable that again and save the
Room. [r2100](https://code.google.com/p/openmeetings/source/detail?r=2100) should fix this already.


## Major Changes in 0.7 RC2 ##

  * Release RC2 has a new User Interface for the Conference Room and some new styling
  * Fixes DB connection Issues with MySQL
  * Adds possibility to draw simultaneously to the whiteboard (moderated) in Conference Room
  * an new Participants Panel in the Conference Modus
  * some Bug-Fixes like the Text-Tool in the Whiteboard is usable again, Clear Chat history, Scaling of Screen-Sharing Images to whiteboard Size.
  * Updated Italian Lang-File
  * Fixed Button to Invitation (moved to Menu Bar)

## Major Changes in 0.7 RC1 ##

Information:

Starting with Release 0.7 OpenMeetings uses a later version of Red5. We will work on updating to the very latest versions continuously to keep in track with the new features. Same for the Client side which is now build with OpenLaszlo 4.2.0 (but still SWF8).

OpenMeetings will only run with the Red5 version recommended in our Instructions out of the box.

Also still experimental recorder does now use FFMpeg to generate a whiteboard.swf for each recording.

Major Changes:

  * OpenMeetings and red5 are together in one zip to prevent users from choosing wrong red5 version
  * updated to Red5 [Revision 3200](https://code.google.com/p/openmeetings/source/detail?r=3200) and OpenLaszlo 4.2.0
  * changed UI for Whiteboard and Whiteboard Tools
  * improved Tools and replaced arrow-tool
  * Property editor
  * Reworked Recorder (still beta), will produce a whiteboard.swf using FFMpeg
  * export whiteboard drawings (beta) does not include Presentation Files
  * LDap auth configuration
  * database changes to !MyISAM for performance
  * changed video / audio settings
  * max clients in room changed (4,8,16,... 1000)
  * resize videos for Meeting and Event Modus
  * database scheme updates (recording features and phone number)
  * reworked Application Adapter to keep better control of Ghost-Connections and handle larger number of Users in each room
  * Possibility to kick User (Administration Panel > Connections), List of connected Users
  * experimental rtmps support
  * fixes Bug to load Documents to Whiteboard with Flash Player 10
  * updated and added language files (thai,ukranian, turkish new)
  * right to left languages

... and a lot more



## Major Changes in 0.5.2 ##

The Release 0.5.2 is a development snapshot

Fixed Issues/new Features:
  * a Pointer Tool, also non Moderators can click on the Whiteboard and everybody will see that.
  * removed the annoying bandwidth warning in the Screen-Sharing Client
  * Screen Sharing Client does stop broadcasting screens to all users if the user leaves the room
  * new Toolbars
  * new translation (Hungarian)
  * new Property Inspector
  * some minor issues like removed browser feature, disabled recording button, changed window resizer
  * a new Servlet for Direct-Login/Invoking SOAP Methods without using SOAP see MethodGateway


## Major Changes in 0.5.1 ##

The Release 0.5.1 is a development snapshot to enable developers and Administrators to use the new SOAP-Gateway to embed OpenMeetings into their existing environment.
The SOAP-Gateway uses Axis2 to generate WSDL-Files and provide Methods, see: SoapMethods.
Besides the SOAP-Gateway some Issue's have been fixed, including a reworked Video-API.

The complete list of Methods can be seen here: SoapMethods

Besides Issues connected to the SOAP-Gateway, additional fixed Issues:
  * [Issue 427](https://code.google.com/p/openmeetings/issues/detail?id=427) - Invitation Mail Link Problem When Set To Port 80
  * [Issue 432](https://code.google.com/p/openmeetings/issues/detail?id=432): ODT/PPT documents not converted with OpenOffice 2.4 and JOD 2.0.1
  * [Issue 436](https://code.google.com/p/openmeetings/issues/detail?id=436): use room\_id for file-system-logic
  * [Issue 439](https://code.google.com/p/openmeetings/issues/detail?id=439): rework messageing to use room\_id everywhere
  * [Issue 407](https://code.google.com/p/openmeetings/issues/detail?id=407): buffer full - stream buffer empty messages repeatedly
  * [Issue 408](https://code.google.com/p/openmeetings/issues/detail?id=408): seems like the client(s) mute but doesn't un-mute
  * [Issue 419](https://code.google.com/p/openmeetings/issues/detail?id=419): make the Singleton Variant Thread-Save
  * [Issue 415](https://code.google.com/p/openmeetings/issues/detail?id=415): Document Converter Broken
  * [Issue 371](https://code.google.com/p/openmeetings/issues/detail?id=371): upload file: bug in conversion
  * [Issue 406](https://code.google.com/p/openmeetings/issues/detail?id=406): recorder does not show recorded video-views next to each other
  * [Issue 414](https://code.google.com/p/openmeetings/issues/detail?id=414): Multiple Videoview in Conference
  * [Issue 421](https://code.google.com/p/openmeetings/issues/detail?id=421): rename package-name from xmlcrm to openmeetings
  * [Issue 425](https://code.google.com/p/openmeetings/issues/detail?id=425): remove duplicated JARs and rewrite ANT-Script

For installation see: InstallationOfRelease051

## 0.5 Beta6 and Beta7 Changes ##

Main Changes:

  * new Layout (Container)
  * new Chat Module see: [ChatModul](ChatModul.md)
  * User Profile with Image
  * advanced Language editing with import/export see: [LanguageEditor](LanguageEditor.md)
  * Backup Module to import/export userdata see: [BackupPanel](BackupPanel.md)
  * Mute Button to turn sound off, if a somebody has bad audio/strong echo/bad bandwidth
  * remote Activity (green Light if somebody is speaking in Video-Box)
  * Japanese Language-File added (thx to H.Kuze from net8/Open-Laszlo.jp)


Minor Changes:

  * improved Local-Storage (Login-Box remembers)
  * improved Dashboard
  * Some Fixes for Administration Panels
  * Better List-view (jump to first/last and change row-count)
  * improved tooltips
  * tests and fixes to run without problems on mysql and postgres

## 0.5 Beta5 Changes ##

Main Changes:

  * improved audio / video quality settings
  * choose a/v quality at login
  * compability to red5-0.6.3
  * reworked video/audio components (now independent from OpenLaszlo Components)
  * Bugfixes
  * fixes video-freeze bug from [Issue178](https://code.google.com/p/openmeetings/issues/detail?id=178)


Minor Changes:

  * repaired invitation-system
  * choose language combobox for invited users

## 0.5 Beta4 Changes ##

Main Changes:

  * SWF Presentation Viewer for Documents see [SWFPresentationViewer](SWFPresentationViewer.md)
  * Auto Cast for Objects (backend)
  * Improved Configuration / Administration
  * Solved Session-Problems
  * Added Dashboard
  * more languages (korean, chinese-simplefied, chinese-traditional, swedish)

Minor Changes:

  * Repaired ScreenViewer (Problems cause of changed JNLP-File)
  * added mysql5 driver instead of mysql3
  * changed splash
  * linked Project Page and News
  * fixings of various bugs

## 0.5 Beta3 Changes ##

Main Changes:

  * Screen Viewer Application see ScreenViewer
  * Installation for Postgres,MySQL and any database supported by hibernate
  * no more database skeleton's (scheme will be created by hibernate)
  * Fully Multi-Plattform compatible (Linux, MacOSX, Windows)
  * Thumbnails in Library for all Uploaded Data


Minor Changes:

  * Removed unnecessary jboss-%.jars (crashes Application with Red5 JMX-Console)
  * Improved docs for OpenOfficeConverter
  * Improved config.xml to use right syntax
  * Repaired Bug with JPG's (did not generate Thumbs)
  * Added batch files for Windows convertion jobs
  * Repaired Organisation Handling (Could not store new Organisation / or add Users to Organisations)
  * Debug Version of the Application added

## 0.5 Beta2 Changes ##

Main Changes:

  * Laszlo Application in solo modus, only one webapp
  * Easier Installation (no openlaszlo seperated installation needed)
  * Language Files added
  * More Document-Types for importing
  * better performance (non proxied)


Details:

  * all images loaded as JPG's
  * Thumbnails in library
  * error and timeout handlers for images (no more blockings if image could not be loaded)
  * less cpu and server stress (cause no images proxing, no second openlaszlo server)
  * rewritten invitation system
  * better feedback in registration form
  * offline installation and running
  * lot of bug fixes

## 0.4 and Previous versions ##

those Versions have been part of the Dokeos E-Learning Platform