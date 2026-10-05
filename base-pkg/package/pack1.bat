@echo off
chcp 65001 >nul
set ARG1=%1
call mvn package -DskipTests
call mvn exec:java -Dexec.mainClass="cn.oyzh.pkg.test.WoaPreHandler" -Dexec.args="%ARG1%" -Dexec.testClasspathScope=test -X
