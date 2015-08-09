## Editing Logo / Text on top ##

In the config.xml there are some configuration variables

```

<!--
    Name of the Application
    this is the Top-Icon Name of the Application
 -->
<currentappname>OpenMeetings 1.8.3 r4343</currentappname>
<!-- 
    URL to the Home of the Application
    this is the URL which is used if you click on the Top-icon
-->
<currentappnameurl>http://openmeetings.googlecode.com</currentappnameurl>
<!--
    URL to Bugs/Support
    this is the URL which is used if you click on *report a bug*
 -->
<currentappnamebugurl>http://code.google.com/p/openmeetings/issues/list</currentappnamebugurl>
```

Addtionally the browser-bar title can be configured in the Openmeetings > Administration > Configuration.<br />
Config Key: `application.name`


## Editing Colors ##

To change the color scheme you can simply edit the config.xml, there are 3 color settings, the whole application is styled using those:

```
<!--
	These colors are the main colors and style the whole application
 -->
<basebgcolorizer>0x669AE6</basebgcolorizer>
<baseMousecolorizer>0x7BA8EA</baseMousecolorizer>
<baseMouseOvercolorizer>0x99BBEE</baseMouseOvercolorizer>
```