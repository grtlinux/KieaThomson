package org.tain.kiea.thomson;

import java.util.ResourceBundle;

import org.tain.utils.ClassUtils;
import org.tain.utils.ResourcesUtils;

/**
 * Project: Reuter Bridge on Kiea Thomson module
 *
 * @author KangSeok_Mac
 *
 * Windows Commands :
 *     DOS> set JAVA_HOME=C:/tain/java/jdk1.8.0_152
 *     DOS> set PATH=%JAVA_HOME%/bin;%PATH%
 *     DOS> java -version
 *
 *     DOS> javac -cp ".;out/lib/*" -d out src/org/tain/runjar/*.java src/org/tain/test/*.java src/org/tain/utils/*.java src/com/thomsonreuters/ema/examples/mrn/*.java src/org/tain/kiea/thomson/*.java
 *     DOS> jar -cvfm KieaThomson.jar src/META-INF/MANIFEST.MF -C out .
 *     DOS> jar -cvfm thomson-1.0.jar src/META-INF/MANIFEST.MF -C out .
 *     DOS> tree /f /a
 *
 *     DOS> java -jar KieaThomson.jar
 *
 *
 *
 */
public class KieaThomsonMain {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (!flag) test01(args);
		if (flag) test02(args);   // new MRNConsumer(args);
	}

	private static void test01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (!flag) {
			String clsName = new Object(){}.getClass().getEnclosingClass().getName();

			ResourceBundle rb = ResourceBundle.getBundle(clsName.replace('.', '/'));

			String desc = rb.getString("org.tain.kiea.thomson.desc");
			String version = rb.getString("org.tain.kiea.thomson.version");

			if (flag) System.out.printf(">>>>> [%s] [%s]%n", desc, version);
		}

		if (flag) {
			String desc = ResourcesUtils.getString("org.tain.kiea.thomson.desc");
			String version = ResourcesUtils.getString("org.tain.kiea.thomson.version");

			if (flag) System.out.printf(">>>>> [%s] [%s]%n", desc, version);
		}
	}

	private static void test02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) {
			String desc = ResourcesUtils.getString("org.tain.kiea.thomson.desc");
			String version = ResourcesUtils.getString("org.tain.kiea.thomson.version");

			if (flag) System.out.printf("----- PROJECT: %s (version %s) -----%n", desc, version);
		}

		new MRNConsumer(args);
	}
}
