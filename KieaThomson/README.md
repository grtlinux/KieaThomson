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
		VBOX__VB_SHARE 볼륨에 대한 폴더 경로의 목록입니다.
		볼륨 일련 번호는 0100-0004입니다.
		E:.
		|   KieaThomson.jar
		|
		+---out
		|   +---org
		|   |   \---tain
		|   |       +---kiea
		|   |       |   \---thomson
		|   |       |           FlatFragBean.class
		|   |       |           AppClient.class
		|   |       |           MRNConsumer.class
		|   |       |           KieaThomsonMain.class
		|   |       |
		|   |       +---runjar
		|   |       |       RunJarLoader$1.class
		|   |       |       RunJarLoader.class
		|   |       |       RunJarLoader$ManifestInfo.class
		|   |       |       RsrcURLConnection.class
		|   |       |       RsrcURLStreamHandlerFactory.class
		|   |       |       RsrcURLStreamHandler.class
		|   |       |
		|   |       \---utils
		|   |               ClassUtils.class
		|   |               GsonUtils$1.class
		|   |               GsonUtils$2.class
		|   |               GsonUtils.class
		|   |
		|   \---lib
		|           gson-2.7.jar
		|
		\---src
		    +---org
		    |   \---tain
		    |       +---kiea
		    |       |   \---thomson
		    |       |           AppClient.java
		    |       |           MRNConsumer.java
		    |       |           FlatFragBean.java
		    |       |           KieaThomsonMain.java
		    |       |
		    |       +---runjar
		    |       |       RsrcURLConnection.java
		    |       |       RsrcURLStreamHandler.java
		    |       |       RsrcURLStreamHandlerFactory.java
		    |       |       RunJarLoader.java
		    |       |
		    |       \---utils
		    |               ClassUtils.java
		    |               GsonUtils.java
		    |
		    \---META-INF
		            MANIFEST.MF












.....
