_available since version 1.9.x_

When you choose the webcam you have different resolutions available the user can choose from.

You can add/remove resolutions and also change the default selection.
In the config.xml there is a section:
```
<availableCameraResolutions>
    <resolution type="4:3 (~6 KByte/sec)" width="40" height="30" isDefault="false" />
    <resolution type="4:3 (~12 KByte/sec)" width="80" height="60" isDefault="false" />
    <resolution type="4:3 (~20 KByte/sec)" width="120" height="90" isDefault="true" />
    <resolution type="QQVGA 4:3 (~36 KByte/sec)" width="160" height="120" isDefault="false" />
    <resolution type="4:3 (~40 KByte/sec)" width="240" height="180" isDefault="false" />
    <resolution type="HVGA 4:3 (~56 KByte/sec)" width="320" height="240" isDefault="false" />
    <resolution type="4:3  (~60 KByte/sec)" width="480" height="360" isDefault="false" />
    <resolution type="4:3 (~68 KByte/sec)" width="640" height="480" isDefault="false" />
    <resolution type="XGA 4:3" width="1024" height="768" isDefault="false" />
    <resolution type="16:9" width="256" height="150" isDefault="false" />
    <resolution type="WQVGA 9:5" width="432" height="240" isDefault="false" />
    <resolution type="pseudo 16:9" width="480" height="234" isDefault="false" />
    <resolution type="16:9" width="512" height="300" isDefault="false" />
    <resolution type="nHD 16:9" width="640" height="360" isDefault="false" />
    <resolution type="16:9" width="1024" height="600" isDefault="false" />
</availableCameraResolutions>
```

Tip: You might edit the config.xml with an XML aware editor that correctly handles the XSD defintion to avoid misconfiguration.
The openmeetings-config.xsd is in the same folder like the config.xml.