@ECHO OFF
REM CALL
REM java -Djava.library.path=./natives -jar rgbiv-1.0-SNAPSHOT.jar <mode>
REM 
REM mode: server | client
REM
REM Additional args 
REM   mode=server:
REM     port
REM     server-name port
REM     server-name interface-ip port
REM
REM   mode=client:
REM     --fullscreen
java -Djava.library.path=./natives -jar rgbiv-1.0-SNAPSHOT.jar client --fullscreen