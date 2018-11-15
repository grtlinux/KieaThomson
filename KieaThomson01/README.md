RunJar05
========
> version: 5.0  
> comment: show menu  

Tools
-----
> jdk 1.8.0_191
> ant 1.9.13
  
> src/META-INF  
> src/META-INF/lib/*.jar <---- here  
> src/META-INF/MANIFEST.MF  

### be job

```
	DOS> java -jar RunJar01.jar

	DOS> set JAVA_HOME=C:\tain\java\jdk1.7.0_79

	DOS> set PATH=%JAVA_HOME%\bin;%PATH%

	DOS> java -version
		java version "1.7.0_79"
		Java(TM) SE Runtime Environment (build 1.7.0_79-b15)
		Java HotSpot(TM) 64-Bit Server VM (build 24.79-b02, mixed mode)

	DOS> ant -version
		Apache Ant(TM) version 1.9.13 compiled on July 10 2018
	
	DOS> ant

		Buildfile: C:\hanwha\workspace\RunJar05\build.xml
		
		delete.target.file:
		     [echo] Delete Target File
		   [delete] Deleting: C:\hanwha\workspace\RunJar05\RunJar05.jar
		
		delete.dir:
		     [echo] Delete Folder
		   [delete] Deleting directory C:\hanwha\workspace\RunJar05\classes
		
		mkdir.dir:
		     [echo] Make Folder
		    [mkdir] Created dir: C:\hanwha\workspace\RunJar05\classes
		
		init:
		     [echo] Initializing
		
		compile:
		     [echo] Compiling
		    [javac] Compiling 9 source files to C:\hanwha\workspace\RunJar05\classes
		     [copy] Copying 7 files to C:\hanwha\workspace\RunJar05\classes
		
		make.jar:
		     [echo] Make Jar File
		      [jar] Building jar: C:\hanwha\workspace\RunJar05\RunJar05.jar
		
		BUILD SUCCESSFUL
		Total time: 1 second

	DOS> ant run.jar

		Buildfile: C:\hanwha\workspace\RunJar05\build.xml
		
		delete.target.file:
		     [echo] Delete Target File
		   [delete] Deleting: C:\hanwha\workspace\RunJar05\RunJar05.jar
		
		delete.dir:
		     [echo] Delete Folder
		   [delete] Deleting directory C:\hanwha\workspace\RunJar05\classes
		
		mkdir.dir:
		     [echo] Make Folder
		    [mkdir] Created dir: C:\hanwha\workspace\RunJar05\classes
		
		init:
		     [echo] Initializing
		
		compile:
		     [echo] Compiling
		    [javac] Compiling 9 source files to C:\hanwha\workspace\RunJar05\classes
		     [copy] Copying 7 files to C:\hanwha\workspace\RunJar05\classes
		
		make.jar:
		     [echo] Make Jar File
		      [jar] Building jar: C:\hanwha\workspace\RunJar05\RunJar05.jar
		
		run.jar:
		     [echo] Run Jar File - Usage
		     [java] >>>>> org.tain.main.UsageMain.main() - UsageMain.java(52)
		     [java] >>>>> org.tain.main.UsageMain.run01() - UsageMain.java(26)
		     [java] >>>>> arguments: []
		     [java] ---------- arg0: test01 -----------
		     [java] COMMENT: test01 project
		     [java] USAGE: java -jar RunJar.jar test01 args...
		     [java]
		     [java] ---------- arg0: test02 -----------
		     [java] COMMENT: test02 project
		     [java] USAGE: java -jar RunJar.jar test02 args...
		     [java]
		     [java] 123,456,789,012
		     [java] 1,234,567,890.120
		
		BUILD SUCCESSFUL
		Total time: 1 second


	DOS> java -jar RunJar05.jar
		>>>>> org.tain.main.UsageMain.main() - UsageMain.java(44)
		>>>>> org.tain.main.UsageMain.run01() - UsageMain.java(18)
		>>>>> arguments: []
		---------- arg0: test01 -----------
		COMMENT: test01 project
		USAGE: java -jar RunJar.jar test01 args...
		
		---------- arg0: test02 -----------
		COMMENT: test02 project
		USAGE: java -jar RunJar.jar test02 args...

	DOS> java -jar RunJar05.jar test01 1 2 3
		>>>>> org.tain.main.Test01Main.main() - Test01Main.java(28)
		>>>>> org.tain.main.Test01Main.run01() - Test01Main.java(18)
		>>>>> [1, 2, 3]

	DOS> java -jar RunJar05.jar test02
		>>>>> org.tain.main.Test02Main.main() - Test02Main.java(28)
		>>>>> org.tain.main.Test02Main.run01() - Test02Main.java(18)
		>>>>> []

	DOS> java -jar RunJar05.jar test03
		Exception in thread "main" java.util.MissingResourceException: Can't find resource for bundle java.util.PropertyResourceBundle, key
		org.tain.runjar.test03
		        at java.util.ResourceBundle.getObject(ResourceBundle.java:395)
		        at java.util.ResourceBundle.getString(ResourceBundle.java:355)
		        at org.tain.runjar.RunJarLoader.run03(RunJarLoader.java:204)
		        at org.tain.runjar.RunJarLoader.main(RunJarLoader.java:288)

```

References
----------
- [Running Apache Ant](https://ant.apache.org/manual/running.html "Running Apache Ant")
- [Java](https://ant.apache.org/manual/Tasks/java.html "Java")
- [Jar](https://ant.apache.org/manual/Tasks/jar.html "Jar")
- [War](https://ant.apache.org/manual/Tasks/war.html "War")
- [ANT를 이용한 WAR 파일 생성하기](http://logtree.tistory.com/11 "ANT를 이용한 WAR 파일 생성하기")
- [Deploy by Ant](http://evilimp.tistory.com/353 "Deploy by Ant")
- [Ant를 사용한 배포 자동화](http://whiteship.tistory.com/1206 "Ant를 사용한 배포 자동화")
- [Ant를 활용한 애플리케이션 빌드 및 배포](http://www.jlancer.net/board/article_view.jsp?article_no=7443&idx_notice=NOTICE_FLAG+DESC%2C&board_no=21 "Ant를 활용한 애플리케이션 빌드 및 배포")
- [원격 서버 Ant 배포(Local Ant Build & Deploy/Undeploy Remote Server)](http://firehouse.tistory.com/25 "원격 서버 Ant 배포(Local Ant Build & Deploy/Undeploy Remote Server)")
- [How do I deploy to Tomcat using Ant?](http://www.avajava.com/tutorials/lessons/how-do-i-deploy-to-tomcat-using-ant.html "How do I deploy to Tomcat using Ant?")
- [Compile, deploy and start Tomcat with Ant script](https://readlearncode.com/code-and-stuff/useful-ant-tasks/ "Compile, deploy and start Tomcat with Ant script")
- [Ant: Problem with Undeploy Task with Tomcat 6](https://coderanch.com/t/474812/build-tools/Ant-Undeploy-Task-Tomcat "Ant: Problem with Undeploy Task with Tomcat 6")
- [Ant Tutorials](http://www.avajava.com/tutorials/categories/ant "Ant Tutorials")
- []("")
- []("")
.....

