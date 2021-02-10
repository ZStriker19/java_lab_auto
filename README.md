### Setup




command to run with auto tracing:  
java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.logs.injection=true -jar build/libs/springtest0-1.0.jar --server.port=9390


Run with auto and add global span tags: `java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.trace.global.tags=[food:grapes] -jar build/libs/springtest0-1.0.jar --server.port=9390`

To build:
./gradlew build


curl localhost:9390/ServiceC



Can also curl ServiceX to hit ServiceY on SpringTest1 `curl --header "name: zach" localhost:9390/ServiceX`


When adding open tracing had to manually set the datadog ot tracing version

When using just the opentracer I ran the command: `java -Ddd.trace.analytics.enabled=true -jar build/libs/springtest0-1.0.jar --server.port=9390` should use port 9390 if connecting with separate application

When tracing using the Ddd.trace.methods system property run: 
java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.trace.annotation.analytics.enabled=true -Ddd.trace.methods="hello.GreetingController[doSomeStuff,doSomeOtherStuff];hello.Randomizer[randomize]"  -Ddd.jmxfetch.enabled=true  -jar build/libs/springtest0-1.0.jar --server.port=9390


When you run service C and service D separately, for service c: java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -jar build/libs/springtest0-1.0.jar --server.port=9390
for service D: java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -jar build/libs/springtest0-1.0.jar --server.port=9393


Run with logs:
java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.trace.analytics.enabled=true -Ddd.jmxfetch.enabled=true -Ddd.trace.debug=true -Ddatadog.slf4j.simpleLogger.logFile=./debug_logs.log -jar build/libs/springtest0-1.0.jar --server.port=9390


To start just this service in debug mode writing logs to NO_dd-trace-ot.log: 
java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.trace.analytics.enabled=true -Ddd.jmxfetch.enabled=true -Ddd.trace.debug=true -Ddatadog.slf4j.simpleLogger.logFile=/Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1_auto/SpringTest0/logs/NO_dd-trace-ot.log -jar build/libs/springtest0-1.0.jar --server.port=9390

To start just this service in debug mode writing logs to NO_dd-trace-ot_with_OkHTTP_dependency_and_usage.log
java -javaagent:/Users/zach.groves/projects/java-apm/dd-trace-java/dd-java-agent/build/libs/dd-java-agent-0.38.0-SNAPSHOT.jar -Ddd.service.name=springtest0 -Ddd.trace.analytics.enabled=true -Ddd.jmxfetch.enabled=true -Ddd.trace.debug=true -Ddatadog.slf4j.simpleLogger.logFile=/Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1_auto/SpringTest0/logs/NO_dd-trace-ot_with_OkHTTP_dependency_and_usage.log -jar build/libs/springtest0-1.0.jar --server.port=9390


To start just this service in debug mode writing logs to with_dd-trace-ot_with_OkHTTP_dependency_and_usage.log
java -javaagent:/Users/zach.groves/projects/java-apm/dd-trace-java/dd-java-agent/build/libs/dd-java-agent-0.38.0-SNAPSHOT.jar -Ddd.service.name=springtest0 -Ddd.integration.okhttp.enabled=true -Ddd.trace.analytics.enabled=true -Ddd.jmxfetch.enabled=true -Ddd.trace.debug=true -Ddatadog.slf4j.simpleLogger.logFile=/Users/zach.groves/projects/my_sandboxes/java_apm_lab/lab1_auto/SpringTest0/logs/with_dd-trace-ot_with_OkHTTP_dependency_and_usage.logs -jar build/libs/springtest0-1.0.jar --server.port=9390

/Users/zach.groves/projects/java-apm/dd-trace-java/dd-java-agent/build/libs/dd-java-agent-0.38.0-SNAPSHOT.jar

DD_KAFKA_CLIENT_PROPAGATION_ENABLED


java -javaagent:./dd-java-agent.jar -Ddd.service.name=springtest0 -Ddd.profiling.enabled=true -XX:FlightRecorderOptions=stackdepth=256 -Ddd.trace.analytics.enabled=true -Ddd.jmxfetch.enabled=true -Ddd.trace.debug=true -Ddatadog.slf4j.simpleLogger.logFile=./debug_logs.log -jar build/libs/springtest0-1.0.jar --server.port=9390

