# Information #

Starting with Release 0.7 OpenMeetings uses a later version of Red5. We will work on updating to the very latest versions continuously to keep in track with the new features. Same for the Client side which is now build with OpenLaszlo 4.2.0 (but still SWF8).

OpenMeetings will only run with the Red5 version recommended in our Instructions out of the box.

Also still experimental recorder does now use FFMpeg to generate a whiteboard.swf for each recording.

# Major Changes #

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