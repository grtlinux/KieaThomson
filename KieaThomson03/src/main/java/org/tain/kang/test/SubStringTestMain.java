package org.tain.kang.test;

import org.tain.kang.utils.ClassUtils;

@SuppressWarnings("unused")
public class SubStringTestMain {

	private static final boolean flag;
	
	static {
		flag = true;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/*
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		if (flag) test01(args);
	}
	*/
	
	private static void test01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		StringBuilder sb = new StringBuilder();
		sb.append("[0x02]REUTASX1kc0K1_1810182BHsjro2aWjS8sUvsKGdf8VKva1c0947VJYV8q 0000200001....");
		
		if (flag) System.out.printf(">>>>> '%s'%n", sb.substring(0, 6));
		if (flag) System.out.printf(">>>>> '%s'%n", sb.substring(6, 10));
		if (flag) System.out.printf(">>>>> '%s'%n", sb.substring(10, 65));
	}
}
