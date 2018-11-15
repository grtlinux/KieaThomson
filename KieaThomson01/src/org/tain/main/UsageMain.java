package org.tain.main;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tain.utils.ClassUtils;

public class UsageMain {

	private static final boolean flag;
	private static final Logger log;
	
	static {
		flag = true;
		log = LoggerFactory.getLogger(UsageMain.class);
	}
	
	///////////////////////////////////////////////////////////////////////////

	private static void test02(String[] args) throws Exception {
		long val = 123456789012L;
		double dbl = 1234567890.12;
		
		if (flag) System.out.printf("%,d%n", val);
		if (flag) System.out.printf("%,.3f%n", dbl);
	}
	
	private static void run01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) {
			if (flag) System.out.println(">>>>> arguments: " + Arrays.asList(args));
		}

		if (flag) {
			log.info("INFO: {}", ClassUtils.getClassInfo());
		}
		
		if (flag) {
			// USAGE: test01
			System.out.println("---------- arg0: test01 -----------");
			System.out.println("COMMENT: test01 project");
			System.out.println("USAGE: java -jar KieaThomson.jar test01 args...");
			System.out.println();
		}
		
		if (flag) {
			// USAGE: test02
			System.out.println("---------- arg0: test02 -----------");
			System.out.println("COMMENT: test02 project");
			System.out.println("USAGE: java -jar KieaThomson.jar test02 args...");
			System.out.println();
		}

		if (flag) {
			// USAGE: test03
			System.out.println("---------- arg0: test03 -----------");
			System.out.println("COMMENT: test03 project");
			System.out.println("USAGE: java -jar KieaThomson.jar test03");
			System.out.println();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		if (flag) run01(args);
		if (flag) test02(args);
	}
}
