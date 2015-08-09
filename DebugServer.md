# Introduction #

During developments, it is sometimes necessary to debug your
application. As the server-side code of openmeetings is written in
Java, it is very easy to do so.

The procedure to set up a debugging environments takes 2 simple steps,
one on the server side (it has to accept the debugging connection) and
one on the development host (to set up the debugging connection).

# Details #

## On the server ##

First of all, you have to build the sources enabling debugging
features. After you have fetched the sources from SVN (see
BuildSources), you have to modify the way they will be built. For that,
simply edit the file build.xml that is located in the root folder of
the server-side code (webapp).

Add the following attributes to all javac tag:
```
debug="true" debuglevel="lines,vars,source"
```

For example, this would give:
```
<javac srcdir="${main.src.dir}" destdir="${main.out.dir}" debug="true"
debuglevel="lines,vars,source" >
```

The following of the procedure is rather generic, simply build and
deploy the webapp in the Red5 server.

However, the JVM running the Red5 server has to be launched with the
right debugging options. You have to pass the following parameter to it:
```
-Xdebug -Xrunjdwp:transport=dt_socket,address=XXXX,server=y,suspend=n
```
where:
  * `XXXX` = the port the application will be listening to for
debug instructions (I'll be using port 1044 for the following instructions)
  * `suspend` = "y" or "n", depending on the fact that you want or
not the debugging tool to be able to suspend the application (I'll
choose "n" for the following instructions)

The way you will pass them to the JVM depends (a little) on the OS of
your server.

### On a Linux server ###
  * Running Red5 as a process
Edit the red5.sh script and add the following line at the beginning:
```
DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=1044,server=y,suspend=n"
```

Finally, add the call to `$DEBUG_OPTS` in the java command at the
end of the file. This would give for example:
```
exec $JAVA $DEBUG_OPTS -Djava.security.manager
-Djava.security.policy=conf/red5.policy -cp red5.jar:conf:$CLASSPATH org.red5.server.Standalone
```

  * Running Red5 as a service
If you start Red5 as a service, the /etc/init.d/red5 script will run
the previously mentioned red5.sh script, so all you need to do is to
edit it, as described above.

### On a Windows server ###
  * Running Red5 as a process
Edit the red5.bat script and add the following line at the beginning:
```
set DEBUG_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=1044,server=y,suspend=n
```

Finally, add the call to `%DEBUG_OPTS%` in the java command at the
end of the file. This would give for example:
```
"%JAVA_HOME%/bin/java" %DEBUG_OPTS% -Djava.security.manager
-Djava.security.policy=conf/red5.policy -cp
lib/sqljdbc.jar;red5.jar;conf;bin;%CLASSPATH% org.red5.server.Standalone
```

  * Running Red5 as a service
If you start Red5 as a service, the launching executable will be
wrapper.exe. In opposition to the way the service is launched on a
Linux server, this will not run the red5.bat script. Instead, you will
have to add the debugging parameters to the wrapper.conf file.

Edit the file conf\wrapper.conf and locate the lines corresponding to
the parameters passed to the JVM (search for the
keys "`wrapper.java.additional.X`" where `X` is the number of
the parameter). Then, simply add the debugging parameters following the
already existing ones. For example, this would give:

```
# Java Additionnal Parameters`
wrapper.java.additional.1="-Djava.security.manager"
wrapper.java.additional.2="-Djava.security.policy=../conf/red5.policy"
wrapper.java.additional.3="-Xdebug"
wrapper.java.additional.4="-Xrunjdwp:transport=dt_socket,address=1044,server=y,suspend=n"
```


## On the development host ##

Once the server is running, with debugging connection enabled, you
have to set up your debugging tool so that it can connect to the remote
application. This will depend on the debugging tool you are using.

### Eclipse users ###
Import the project in your workspace (you might already have done that
if you use Eclipse :P) :
File -> Import -> General -> Existing project into workspace
Then browse your filesystem to find the "webapp" folder (in the trunk
that you fetched with SVN). As there is already a .project file for
Eclipse, you can validate. You should now have the server-side code in
a workspace project. At the time of writing, the project is called "openmeetings\_webapp".

As the project is built using Ant, it should be the same in Eclipse
(so that compiled classes in Eclipse are identical to the ones deployed
on the server). This should already be done as the eclipse-builder
configuration is commited on the SVN. However, if it is not the case,
you can add it. To do so, click Project -> Properties -> Builders ->
New -> Ant builder. Give it the name you want and fill the required
fields as specified on the screen capture below.

![http://openmeetings.googlecode.com/svn/wiki/AntBuilder.png](http://openmeetings.googlecode.com/svn/wiki/AntBuilder.png)

After validating, Eclipse should build your project.

Now, you can set up the debugging connection to the server. Click Run
-> Open Debug dialog. Select "Remote Java Application" and then click "New".

Choose a name for your debugging connection and then specify the Red5
server IP in the "Host" field as well as the port you specified as a
parameter to the JVM (I used 1044 above). Your set up is now complete.

![http://openmeetings.googlecode.com/svn/wiki/Debug.png](http://openmeetings.googlecode.com/svn/wiki/Debug.png)

When you click "Debug", the configuration will be saved and Eclipse
will open the debugging connection.

You can now set breakpoints, when you run openmeetings in your web
browser, the execution will stop on them.

### "Your-favorite-tool" users ###
// add instructions for debugging with your favorite tool.