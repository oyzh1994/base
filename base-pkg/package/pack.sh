#!/bin/bash
ARG1=$1
mvn package -DskipTests
#mvn test-compile
mvn exec:java -Dexec.mainClass="cn.oyzh.pkg.test.WinArmPreHandler" -Dexec.args="$ARG1" -Dexec.testClasspathScope=test -X