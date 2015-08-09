# Introduction #

Automatic tests are the significant part of development process. Below is the instruction of how to run Openmeetings [JUnit](http://junit.org) tests.


# Details #

  1. running tests using command line:
    * all tests:
```
ant test
```
    * subset of tests by mask:
```
ant -Dtestcases=TestError* test
```
  1. running tests from Eclipse IDE:
    * copy src/META-INF/**yourdb`_`**persistence.xml to src/META-INF/persistence.xml
    * Start Eclipse
    * Load workspace with Openmeetings project (or refresh existing project)
    * Select "Debug Configurations..."
![http://openmeetings.googlecode.com/svn/docs/junit/DebugConfigurations.png](http://openmeetings.googlecode.com/svn/docs/junit/DebugConfigurations.png)
    * Create New Junit configuration, give it a name and select test class
![http://openmeetings.googlecode.com/svn/docs/junit/NewConfiguration.png](http://openmeetings.googlecode.com/svn/docs/junit/NewConfiguration.png)
    * Select Arguments tab
    * Add the following code to the VM section
```
-javaagent:${workspace_loc:ROOT}/lib/openjpa2/openjpa-2.1.0.jar -Dwebapps.root=__<FULL PATH TO OPENMEETINGS>__\dist\red5\webapps\openmeetings
```
![http://openmeetings.googlecode.com/svn/docs/junit/Arguments.png](http://openmeetings.googlecode.com/svn/docs/junit/Arguments.png)
    * Select Debug