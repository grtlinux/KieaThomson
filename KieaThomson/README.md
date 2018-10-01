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



-----

The below sources is version up sources with RunJar module...

	DOS> javac -cp ".;out/lib/*" -d out src/org/tain/kiea/thomson/*.java src/org/tain/runjar/*.java src/org/tain/test/*.java src/org/tain/utils/*.java src/com/thomsonreuters/ema/examples/mrn/*.java

	DOS> jar -cvfm KieaThomson.jar src/META-INF/MANIFEST.MF -C out .

	DOS> tree /f
		VBOX__VB_SHARE 볼륨에 대한 폴더 경로의 목록입니다.
		볼륨 일련 번호는 0100-0004입니다.
		E:.
		└─src
		    │  resources.properties
		    │
		    ├─org
		    │  └─tain
		    │      ├─kiea
		    │      │  └─thomson
		    │      │          FlatFragBean.class
		    │      │          AppClient.class
		    │      │          AppClient.java
		    │      │          MRNConsumer.java
		    │      │          FlatFragBean.java
		    │      │          KieaThomsonMain$1.class
		    │      │          MRNConsumer.class
		    │      │          KieaThomsonMain.class
		    │      │          KieaThomsonMain.java
		    │      │
		    │      ├─runjar
		    │      │      RunJarLoader$1.class
		    │      │      RunJarLoader.class
		    │      │      RsrcURLConnection.java
		    │      │      RunJarLoader$ManifestInfo.class
		    │      │      RsrcURLStreamHandler.java
		    │      │      RsrcURLConnection.class
		    │      │      RsrcURLStreamHandlerFactory.java
		    │      │      RsrcURLStreamHandlerFactory.class
		    │      │      RsrcURLStreamHandler.class
		    │      │      RunJarLoader.java
		    │      │
		    │      ├─test
		    │      │      UdpRecv.java
		    │      │      UdpServerMain.class
		    │      │      UdpServerMain.java
		    │      │      UdpClientMain.class
		    │      │      UdpSend.class
		    │      │      UdpRecv.class
		    │      │      UdpClientMain.java
		    │      │      UdpSend.java
		    │      │
		    │      └─utils
		    │              ClassUtils.class
		    │              GsonUtils$1.class
		    │              ClassUtils.java
		    │              ResourcesUtils.java
		    │              GsonUtils$2.class
		    │              GsonUtils.class
		    │              ResourcesUtils.class
		    │              GsonUtils.java
		    │
		    ├─META-INF
		    │      MANIFEST.MF
		    │
		    ├─lib
		    │      xpp3-1.1.4c.jar
		    │      commons-logging-1.2.jar
		    │      upaValueAdd-3.2.1.0.jar
		    │      commons-configuration-1.10.jar
		    │      json-20180813.jar
		    │      jfxrt.jar
		    │      upa-3.2.1.0.jar
		    │      ema-3.2.1.0-javadoc.jar
		    │      ema-3.2.1.0.jar
		    │      commons-collections-3.2.2.jar
		    │      upaValueAddCache-3.2.1.0.jar
		    │      ansipage-3.2.1.0.jar
		    │      slf4j-jdk14-1.7.12.jar
		    │      gson-2.7.jar
		    │      jdacsUpalib.jar
		    │      commons-lang-2.6.jar
		    │      slf4j-api-1.7.12.jar
		    │
		    └─com
		        └─thomsonreuters
		            └─ema
		                └─examples
		                    └─mrn
		                            MRNFXMain.java
		                            MRNConsumer.java

-----
After unzip KieaThomson.jar, and run jar command.

	DOS> jar -cvfm KieaThomson.jar KieaThomson/META-INF/MANIFEST.MF -C KieaThomson .

-----
Linux module



.....
