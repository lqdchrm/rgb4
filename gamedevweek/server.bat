echo "ABCD"
echo %1
echo %2
echo %3
java -Djava.library.path=../ext/lib/native -jar server/server.jar %1 %2 %3
pause