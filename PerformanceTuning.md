## Introduction ##

To use the optimum of performance from your server there is a start-script for red5 available that does some basic settings in the JVM.
It is called red5-highperf.sh. It is recommended to use that script for production and high-load scenarios.

```
#!/bin/bash

if [ -z "$RED5_HOME" ]; then export RED5_HOME=.; fi

# Previous option set
export JAVA_OPTS="-Xrs -Xms512M -Xmx1024M -Xss128K -XX:NewSize=256m -XX:SurvivorRatio=16 -XX:MinHeapFreeRatio=20 -XX:+ExplicitGCInvokesConcurrent -Djava.net.preferIPv4Stack=true -Xverify:none"

# start Red5
echo "Setting Hi Performance Options"
exec $RED5_HOME/red5.sh >> $RED5_HOME/log/jvm.stdout 2>&1 &
```

You have to exclude the "-XX:+UseConcMarkSweepGC" param from the default red5-highperf.sh to make it functional!
However if you are a performance expert you might also play with the values to find perfect matches for your use-case.

For a complete overview about possible arguments and their meaning see:
http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html#PerformanceTuning