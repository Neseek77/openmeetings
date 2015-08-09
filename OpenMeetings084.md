Release 0.8.4 basically adds some new SOAP Functions for directly entering and editing a Room. Some Room-Attributes have been removed that concern the Layout of a Room. However you may still use the previous SOAP Calls but it is recommended to Update to use the latest API-Calls see [SoapMethods](SoapMethods.md).

The Moodle Plugin enables you now to use more feature of OpenMeetings:
  * Create both Room Types Conference Room (Multiple Videos) and Event Room (Single Bigger Video)
  * Set an individual Room Language and Max Number of Participant per Room
  * Set a Flag for a Room so that Students and Guests have to wait and will NOT become the Moderation Role in a Conference Room by default, they will have to wait untill a Moodle-Teacher, -Admin or -Creating-Teacher comes and enters a Room.

We would like to thank all Contributors, User, Developers of OpenMeetings and [Open Kvarken](http://www.openkvarken.fi/) for supporting us.


## Note ##

In the Administration I just encountered an Issue with 0.8.4:
In the Admin Panel of Rooms as => the Admin-Panel expects that there is at least a
number in the Field **demo-Time** ... so when you try to add a new Room it says
System Error. You can workaround that by enabling **Demo Room** (the checkbox)
enter a number in **demo Time** .. you can then disable that again and save the
Room. [r2100](https://code.google.com/p/openmeetings/source/detail?r=2100) should fix this already.