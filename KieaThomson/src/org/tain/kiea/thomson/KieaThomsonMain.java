package org.tain.kiea.thomson;

import org.tain.utils.ClassUtils;

public class KieaThomsonMain {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
	}
}
