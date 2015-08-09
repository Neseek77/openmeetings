_this is available since version 1.9.x_

## Default Configuration ##

FFMPEG has a change in its API (possibly since GIT revision 88bfe4518)

Since Version 1.9.x of OpenMeetings:
By default OpenMeetings expects you to have a new version of FFMPEG on your OpenMeetings server installed and it will use the new API style to communicate with FFMPEG.

## Switching to old FFMPEG API ##

If you want to switch to the old style you can change that using the config key:
`use.old.style.ffmpeg.map.option`

Set this value to `1` to have the old params in the API call to FFMPEG.


References:
  * http://ffmpeg-users.933282.n4.nabble.com/Invalid-stream-specifier-0-Stream-map-0-0-matches-no-streams-Changes-in-FFMPEG-map-option-td4091165.html