package org.tain.kang.thomson;

import java.util.ResourceBundle;

import org.tain.kang.utils.ClassUtils;
import org.tain.kang.utils.ResourcesUtils;

/**
 * Project: Reuter Bridge on Kiea Thomson module
 *
 * @author KangSeok_Mac
 *
 *
 *
 */
@SuppressWarnings("unused")
public class KieaThomsonMain {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	/*
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (!flag) test01(args);
		if (flag) test02(args);   // new MRNConsumer(args);
	}
	*/

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

	public static void test02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) {
			String desc = ResourcesUtils.getString("org.tain.kiea.thomson.desc");
			String version = ResourcesUtils.getString("org.tain.kiea.thomson.version");

			if (flag) System.out.printf("----- PROJECT: %s (version %s) -----%n", desc, version);
		}

		new MRNConsumer(args);
	}
}
