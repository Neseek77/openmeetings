

&lt;H1 LANG="en-US" CLASS="western" ALIGN=CENTER&gt;

Using OpenMeetings as
instant communication server.

&lt;/H1&gt;




&lt;P LANG="en-US" ALIGN=CENTER&gt;&lt;BR&gt;&lt;BR&gt;




&lt;/P&gt;




&lt;H2 LANG="en-US" CLASS="western"&gt;

Introduction

&lt;/H2&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;

Have
you ever thought about making a deep integration of OpenMeetings into
your own site? Like making a registered user being able to conference
with another one, if they are both reading the same site at the
moment?

&lt;/FONT&gt;



&lt;/P&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;

With
the creation of instant communication server script pack you now can
implement instant communication using OpenMeetings server on any of
your CMS sites, or anywhere else. In order to achieve the goal you just have to include the client JavaScript code whenever you know the logged in user unique ID and display name(optional). After the page is loaded, the code contacts the server and becomes available for being conferenced. So, you implement some of the JavaScript functions on the client side, such as client behavior in cases of being logged on and off, receiving a call(conference request), set some variables in scripts, such as server script location, OpenMeetings server location, set up a small database using our database creation script, and have fun. What's best is that only one page out of all loaded from the same browser on the same site eats the traffic. Others use cookies to reflect the master page. If the master is closed, another page becomes the master, and so on.

&lt;/FONT&gt;



&lt;/P&gt;




&lt;H2 LANG="en-US" CLASS="western"&gt;

Instant communication server script
pack contents

&lt;/H2&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;

Our script pack contains:

&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

om\_server.php

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

a php script that acts as instant communication server, processing logging on and off of  your site's users, and establishing calls between them. After the call has been arranged, it creates a room on the OpenMeetings server scripted inside, and gives the unique links to that room to the users. Uses MySQL as a database for storing	data.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

openMeetingsAjaxClientLibrary.js

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

a JavaScript program that is responsible for all the client functionality with the above server, receives commands in the form of  functions, and responds in callbacks.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

masterSlaveDeterminator.js

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

a traffic optimization JavaScript program, that wraps the above JavaScript to decrease the traffic volume. Now, having several windows with the same page opened only one of them will work as a communication client, others will just pass commands and reaction from one to another.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

jquery.cookies.2.1.0.js


&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

this well-known cookies library was used to simplify the work of the JavaScript programs 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

uuid.js

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

library to implement GUID-like string constants generation

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

databaseCreationScript.sql

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

SQL	database creation script for your MySQL database.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;LI&gt;



&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

test\_functionality.php

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;

,

&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

user.tmpl

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;

,

&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

invite.tmpl

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;

 - 

&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

a sample client code that uses the above 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

functionality, implementation of the similar code on your web site will give you the instant communication functionality with OpenMeetings. Has two HTML/JavaScript templates, for entering the required user data, and a template for a user page with all the communication functionality.
Override the

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;

 

&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

user.tmpl

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

 file if you want to test your variant of interface code, then add the code to your page.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P LANG="en-US"&gt;



&lt;BR&gt;



&lt;H2 LANG="en-US" CLASS="western"&gt;

Required steps to make everything
work

&lt;/H2&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;

In
order for everything to work, you need to have the following data
scripted:

&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

in

&lt;/SPAN&gt;



&lt;/FONT&gt;




&lt;I&gt;



&lt;B&gt;

om\_server.php:

&lt;/B&gt;



&lt;/I&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in; margin-bottom: 0in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

define("OM\_ADMIN\_LOGIN",


&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

OpenMeetings Admin Login 

&lt;/SPAN&gt;



&lt;/FONT&gt;

);

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in; margin-bottom: 0in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

define("OM\_ADMIN\_PASS",


&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

OpenMeetings Admin Password

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;




&lt;/SPAN&gt;

);

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in; margin-bottom: 0in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

define("OM\_SERVER\_ADDRESS",


&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT COLOR="#ff0000"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;

OpenMeetings
Server Address

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;




&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

);

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in; margin-bottom: 0in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

$link
= mysql\_connect(

&lt;FONT COLOR="#ff0000"&gt;

MySql
server address - the one that has a database created with

&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;




&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

databaseCreationScript.sql

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;


, 

&lt;/SPAN&gt;



&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

username

&lt;/SPAN&gt;



&lt;/FONT&gt;

,


&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

password

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;




&lt;/SPAN&gt;

);

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

in 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;I&gt;



&lt;B&gt;

openMeetingsAjaxClientLibrary.js:

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;

	var
server\_address = 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT COLOR="#ff0000"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;

URL
with location of 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;




&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;



&lt;I&gt;



&lt;B&gt;

om\_server.php

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;




&lt;/P&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;FONT SIZE=3&gt;

in
client code (sample:

&lt;/FONT&gt;




&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;I&gt;



&lt;B&gt;

user.tmpl

&lt;/B&gt;



&lt;/I&gt;



&lt;/FONT&gt;

):

&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;

	sv\_setserveraddress(


&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT COLOR="#ff0000"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;

URL
with location of 

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;



&lt;I&gt;



&lt;B&gt;

om\_server.php

&lt;/B&gt;



&lt;/I&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New, monospace"&gt;

);

&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;

	sv\_setuserinfo(


&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

User ID

&lt;/SPAN&gt;



&lt;/FONT&gt;

, 

&lt;FONT COLOR="#ff0000"&gt;



&lt;SPAN LANG="en-US"&gt;

User
display name

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;SPAN LANG="en-US"&gt;

 

&lt;/SPAN&gt;

);

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P LANG="en-US"&gt;



&lt;/P&gt;




&lt;P STYLE="margin-top: 0.17in"&gt;

 

&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;FONT SIZE=3&gt;&lt;SPAN LANG="en-US"&gt;

As you see, by the time the client JavaScript code is displayed you should know which user is being shown, so please include this code into the page only if you know the user display name and his ID (any key, unique for each user). After everything has been scripted and you've made sure OpenMeetings server is up and running, you should only implement the reaction of the client code on different events, such as incoming call (	conferencing request ), reaction on your own call and such, all	those method samples already exist in 

&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;I&gt;



&lt;SPAN STYLE="text-decoration: none"&gt;



&lt;B&gt;

user.tmpl

&lt;/B&gt;



&lt;/SPAN&gt;



&lt;/I&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

 file, which you can study.

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;



&lt;SPAN LANG="en-US"&gt;

Here is the short description of every method you will need to override:

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;



&lt;B&gt;

function 	my\_onstatuschange( 

&lt;/B&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;B&gt;

status ) - 

&lt;/B&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="en-US"&gt;

Your

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;SPAN LANG="ru-RU"&gt;

 online status has changed

&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_onmakecall( result )

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

 

&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-
You have made a call, false result means user you called is offline

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_onincomingcall( callerName, link )

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;




&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-
Incoming call from callerName, if you want to start conferencing, go
to the link

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_oncallanswered( calleeName, link )

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;




&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

- Your
call to calleeName has been answered - you can go to the link

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_oncallrejected( calleeName )

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;




&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

- Your
call to calleeName has been rejected

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_onstatusmessage( message )

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

 -

&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;


You have a debug status message

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_masterstatuschanged( masterStatus ) 

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-


&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

Your page
in browser has become a master. This means only code in this page
works with the server, others reflect it(to save the traffic)

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_clearincoming()

&lt;/B&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

 

&lt;/SPAN&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-
Clear information about last incoming call

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_clearanswered() 

&lt;/B&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-
Clear information about last answered call

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P STYLE="margin-left: 0.17in"&gt;



&lt;FONT FACE="Courier New CYR, monospace"&gt;



&lt;FONT SIZE=2 STYLE="font-size: 9pt"&gt;



&lt;B&gt;

function
my\_clearrejected() 

&lt;/B&gt;



&lt;SPAN LANG="en-US"&gt;



&lt;SPAN STYLE="font-weight: normal"&gt;

-
Clear information about last rejected call

&lt;/SPAN&gt;



&lt;/SPAN&gt;



&lt;/FONT&gt;



&lt;/FONT&gt;



&lt;/P&gt;




&lt;P LANG="en-US" STYLE="margin-top: 0.17in"&gt;



&lt;FONT FACE="Arial, sans-serif"&gt;

The pack can be checked out of SVN with the following URL: <a href='http://openmeetings.googlecode.com/svn/trunk/plugins/instant_communication_plugin'><a href='http://openmeetings.googlecode.com/svn/trunk/plugins/instant_communication_plugin'>http://openmeetings.googlecode.com/svn/trunk/plugins/instant_communication_plugin</a></a>

&lt;/FONT&gt;



&lt;/P&gt;

