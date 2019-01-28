package org.tain.kang.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tain.kang.utils.ClassUtils;

@SuppressWarnings("unused")
public class ElapsedTimeTestMain {

	private static final boolean flag;
	private static final Logger log;
	
	static {
		flag = true;
		log = LoggerFactory.getLogger(new Object(){}.getClass().getEnclosingClass());
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/*
	public static void main(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		if (flag) test01(args);
	}
	*/
	
	private static void test01(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());

		if (flag) {
			long tm1 = new Date().getTime();
			
			try { Thread.sleep(2000); } catch (InterruptedException e) {}
			
			long tm2 = new Date().getTime();
			
			long tm9 = tm2 - tm1;
			
			if (flag) log.info("INFO: {} = {} - {}", tm9, tm2, tm1);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.KOREA);
			
			if (flag) log.info("INFO: {}", simpleDateFormat.format(new Date(20000000)));
			
			if (flag) log.info("INFO: {}", getTime(20000000));
		}
	}
	
	private static String getTime(long miliSeconds) {
		long msec = 0;
		long sec = 0;
		long min = 0;
		long hour = 0;
		
		sec = miliSeconds / 1000;
		min = sec / 60;
		hour = min / 60;
		
		min = min % 60;
		sec = sec % 60;
		msec = miliSeconds % 1000;
		
		return String.format("%02d:%02d:%02d.%03d", hour, min, sec, msec);
	}
}
