package org.tain.kiea.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tain.utils.ClassUtils;

public class UsageMain {

	private static final boolean flag;
	private static final Logger log;
	
	static {
		flag = true;
		log = LoggerFactory.getLogger(new Object() {}.getClass().getEnclosingClass());
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		if (flag) test01(args);
	}
	
	private static void test01(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		if (flag) {
			System.out.println("USAGE: ");
		}
	}
}
