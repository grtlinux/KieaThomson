# Kiea Thomson

This project is for Thomson.

	DOS> java -jar KieaThomson.jar


	DOS> set JAVA_HOME=C:\tain\java\jdk1.8.0_152

	DOS> set PATH=%JAVA_HOME%\bin;%PATH%

	DOS> java -version
		java version "1.8.0_152"
		Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
		Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)

	DOS> javac -cp ".;out/lib/*" -d out src/org/tain/runjar/*.java src/org/tain/utils/*.java src/org/tain/kiea/thomson/*.java

	DOS> jar cvfm KieaThomson.jar src/META-INF/MANIFEST.MF -C out .

	DOS> java -jar KieaThomson.jar

	DOS> tree /f /a

.....
