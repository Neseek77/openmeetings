Place it in the folder red5-screenshare and run the command "ant"
To clear the client-side cache from JavaWebstart:
Open a console and type: javaws -uninstall

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE project>
<project name="openmeetings" basedir="./" default="sign">


<target name="sign">
                <delete file="filetest.keystore" />
                <genkey alias="filetest"
          storepass="secret"
          keystore="filetest.keystore"
          verbose="true">
                        <dname>
                                <param name="CN" value="Sebastian Wagner"/>
                                <param name="OU" value="technology"/>
                                <param name="O"  value="openmeetings.googlecode.com"/>
                                <param name="C"  value="DE"/>
                        </dname>
                </genkey>
                <signjar
            alias="filetest" keystore="filetest.keystore"
            storepass="secret"
            lazy="false"
            >
                        <path>
                                <fileset dir="${basedir}" >
                                        <include name="**/*.jar" />

                                </fileset>
                        </path>
                </signjar>
        </target>


</project>
```