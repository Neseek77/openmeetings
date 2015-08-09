# Openmeetings LZX Coding Guidelines #


## (1) Naming Convention ##
| **Type** | **Rules** | **Examples** |
|:---------|:----------|:-------------|
| class    | User-defined general class. Use **lowerCamelCase** for a class name  | `<myClass />` |
| private class | User-defined private class. Use **lowerCamelCase** for a private class name. A private class is used only in the general class. |`<_myPrivateClass />`  |

## (2) Coding Rules ##
### Rules in a class ###
**If possible** , do not use **"parent.**something" within a class in order to access the values outside of the class.

Example:
```
<!-- NG -->
<class name="myClass">
  <view width="${parent.parent.parent.width}" />
</class>
```


### Debug statement ###
Use **if($debug) Debug.write(.....)** for the Debug statements.
Just for a performance issue.

Example:
```
<!-- Not Bad, but Not Recommended -->
<handler name="onsomething">
  Debug.write("something",obj);
</handler>

<!-- OK -->
<handler name="onsomething">
  if($debug) Debug.write("something",obj);
</handler>
```

### Class File ###
**One class per file**, with filename same as classname.
Small private classes only for the general class should be encapsulated in the same file.

Example:
```
<!-- OK -->
myClass1.lzx:
 <library>
   <class name="myClass1" /> 
 </library>

<!-- OK -->
myClass1.lzx:
 <library>
   <class name="myClass1" />
   <class name="_myPrivateClass1" />  <== It is a class needed for myClass1 only
 </library>

<!-- NG -->
myClass1.lzx:
 <library>
   <class name="myClass1" />  <== general class
   <class name="myClass2" />  <== second general class. But there must be *one* general class per file.
 </library>
```

### Comments ###
Use Lzxdoc style comment tags` <---  --> ` for class, attribute, method.
We can generate the LZX Document from the comments.
See also [LzxdocTool](http://wiki.openlaszlo.org/LzxdocTool)

Example:
```
 Normal comment: <!-- normal comment -->
 For LzxDoc comment: <!--- Lzxdoc comment -->   <== Notice! It has more than two Hyphens.
```

Examples:
```
    <!---
        A layout specifically for toolbar buttons, with optional
        automatic button "collapsing".

        Sample usage:
        @START_CODE
        <canvas>
            <include href="../toolBarLayout.lzx"/>
            ...
        </canvas>
        @END_CODE
    -->
    <class name="toolBarLayout">
        <!--- A pixel amount to use between each view in the layout -->
        <attribute name="spacing" value="2"/>

        <!--- The number of items controlled by the layout
              @keywords readonly -->
        <attribute name="numitems" value="0"/>

        <!--- reference that is set on the tag, but cannot be set via javascript 
              @keywords final-->
        <attribute name="vref" value="null"/>

        <!--- @keywords private -->
        <method name="init">
           ...
        </method>

        <!--- @param string name: name of current button, use empty string to get first button name
              @returns string: name of previous named button in current layout -->
       <method name="getPreviousButtonName" args="name">
           ...
       </method>
    </class>
```

The following keywords are allowed:
```
  @START_CODE / @END_CODE
      Use these to surround class-level sample code.  Use in class comments only.

  @keywords
      "private" - docs will not be generated for this element.

  @param type name description
      Use to document method parameters.  If name does not match an actual parameter
      name for the method in question, the tool will display a warning.

  @returns type description
      Use to document the return value of a method.
```