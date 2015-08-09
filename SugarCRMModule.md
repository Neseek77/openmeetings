

## Sugar CRM Demo ##

<a href='http://www.youtube.com/watch?feature=player_embedded&v=wBxG7w_rddQ' target='_blank'><img src='http://img.youtube.com/vi/wBxG7w_rddQ/0.jpg' width='425' height=344 /></a>

Demo-Server: http://www.openmeetings.de:8090/sugar/crm/<br />
User: test<br />
Pass: test<br />
<br />
Documentation:
[Documentation as PDF](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng.pdf)

Page at SugarForge: http://www.sugarforge.org/projects/openmeetings/<br />

## Sugar CRM Module Installation ##

**Requirements**

  * Openmeetings (>= Version 1.2) Openmeetings Admin Account
  * SugarCRM installation (minimum 5.1.0)


Go to Admin- Modul Loader  select Openmeetings\_Connector.zip for Upload und upload the zip file.
![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_1.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_1.png)

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_2.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_2.png)

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_3.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_3.png)

After upload you can install the Openmeetings\_Connector module.

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_4.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_4.png)

After installation a shortcut “Openmeetings Account” in Admin View is available.

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_5.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_5.png)

Configure the Openmeetings Account in the edit view.

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_6.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_6.png)

**After installation and configuration you have to add following Code manually.**

**Path:** include/language/en\_us\_notify\_template.html
Insert this two lines in the Meeting\_Subject <!-- BEGIN: Meeting\_Subject -->   to modifikate the invitations E-mail tamplate after „Description: {MEETING\_DESCRIPTION}“

| Line | Code |
|:-----|:-----|
| 119  | Meeting URL: <{MEETING\_URl}>  |

**Path:** modules/Meetings/Meeting.php
insert in the function set\_notification\_body($xtpl, &$meeting) folowing line for add a Meeting-URL to E-mail template.

| Line | Code |
|:-----|:-----|
| 460  | $xtpl->assign("MEETING\_URl", $meeting->current\_notify\_user->online\_meeting\_url\_temp);   |

**Path:** /modules/DynamicFields/templates/Files/EditView.php

> When including this file from modules/Users/EditView.php,
> the variable handling the template isn't called $xtpl, but $sugar\_smarty

insert following lines to in the
if(isset($focus->custom\_fields)){} body.

| Line | Code |
|:-----|:-----|
| 44   | if (is\_null($xtpl)) { $xtpl = $sugar\_smarty; }|


## Sugar CRM Module Usage ##

After the Installation you have a Link to your personal Meeting Room in you activities section of sugarCRM

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_7.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_7.png)

When you add a new Meeting you will have the possibility to add a OpenMeetings Conference Room for every Meeting. Every participant will receive a direct link to the Conference Room. The Link in the Mail for the invited users is only valid for the Time of the Meeting!

In the Meetings-Details View of SugarCRM you can access the Room at any time.

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_8.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_8.png)

Schedule Onlinemeeting

![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_9.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_9.png)

In the Schedule Meeting screen you can now select check-box (red arrow) for online-meeting with Openmeetings. After you add invitees (green arrow) you can send invites (blue arrow) and the invitee recive a E-mail with a link to a Openmeetings Room.

Example Link in the invitations mail.
Meeting URL:
http://localhost:5080/openmeetings/?invitationHash=d948f8977f33ccb31d77e9d08bf15403

In the detail view you can access the openmeetings-room by the Link.
![http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_10.png](http://openmeetings.googlecode.com/svn/docs/sugar/Docu_install_eng_10.png)