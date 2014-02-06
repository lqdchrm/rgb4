@ECHO OFF

REM building
ECHO Building...
call mvn clean package > build.log
ECHO ... finished

REM copy zip
ECHO Generating Zip...
COPY rgbiv\target\*.zip .\
