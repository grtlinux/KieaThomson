package org.tain.kang.utils;

import java.util.ResourceBundle;
import java.util.Set;

public class ResourcesUtils {

	private static final boolean flag;

	private static final ResourceBundle resourceBundle;

	static {
		flag = true;

		resourceBundle = ResourceBundle.getBundle("resources");

		if (flag) printAll();
	}

	///////////////////////////////////////////////////////////////////////////

	public static String getString(String key) {
		return resourceBundle.getString(key);
	}

	public static void printAll() {
		if (flag) {
			Set<String> keySet = resourceBundle.keySet();
			for (String key : keySet) {
				String val = resourceBundle.getString(key);
				System.out.printf("Resources: '%s' => '%s'%n", key, val);
			}
		}
	}
}
