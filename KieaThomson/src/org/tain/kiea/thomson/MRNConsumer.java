package org.tain.kiea.thomson;

import org.tain.utils.ClassUtils;

public class MRNConsumer {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	public MRNConsumer(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

	}
}
