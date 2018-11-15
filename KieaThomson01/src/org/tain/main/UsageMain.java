package org.tain.main;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tain.utils.ClassUtils;

public class UsageMain {

	private static final boolean flag;
	
	static {
		flag = true;
	}
	
	///////////////////////////////////////////////////////////////////////////

	private static void test01(String[] args) throws Exception {

		if (flag) {
			String strJson = "{ 'id': 123, 'name': 'Kiea', 'age': 30, 'addr': 'Seoul' }";
			JSONObject jsonObject = new JSONObject(strJson);
			String strJsonObject = jsonObject.toString(2);
			if (flag) System.out.println(">>>>> " + strJsonObject);
			
			Long id = jsonObject.has("id") ? jsonObject.getLong("id") : null;
			String name = jsonObject.has("name") ? jsonObject.getString("name") : null;
			Integer age = jsonObject.has("age") ? jsonObject.getInt("age") : null;
			String addr = jsonObject.has("addr") ? jsonObject.getString("addr") : null;
			String content = jsonObject.has("content") ? jsonObject.getString("content") : null;
			if (flag) System.out.printf(">>>>> [%s] [%s] [%s] [%s] [%s]%n", id, name, age, addr, content);
		}
		
		if (flag) {
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("id", 123);
			jsonObject.put("name", "Kiea");
			jsonObject.put("age", 30);
			jsonObject.put("addr", "Seoul");
			
			if (flag) System.out.println(">>>>> " + jsonObject.toString(2));
		}
		
		if (flag) {
			JSONObject jsonObject = new JSONObject();
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.put("arr-1");
			jsonArray.put("arr-2");
			jsonArray.put("arr-3");
			jsonArray.put("arr-4");
			
			jsonObject.put("arr", jsonArray);
			
			if (flag) System.out.println(">>>>> " + jsonObject.toString(2));
		}
	}

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
			// USAGE: test01
			System.out.println("---------- arg0: test01 -----------");
			System.out.println("COMMENT: test01 project");
			System.out.println("USAGE: java -jar RunJar.jar test01 args...");
			System.out.println();
		}
		
		if (flag) {
			// USAGE: test02
			System.out.println("---------- arg0: test02 -----------");
			System.out.println("COMMENT: test02 project");
			System.out.println("USAGE: java -jar RunJar.jar test02 args...");
			System.out.println();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
		
		if (flag) run01(args);
		if (flag) test01(args);
		if (flag) test02(args);
	}
}
