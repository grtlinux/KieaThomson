package org.tain.kang.test;

import java.util.HashMap;
import java.util.Map;

import org.tain.kang.utils.ClassUtils;
import org.tain.kang.utils.GsonUtils;

@SuppressWarnings("unused")
public class BodyHeaderMain {

	private static final boolean flag = true;

	/*
	public static void main(String[] args) {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) test01(args);
	}
	*/

	private static void test01(String[] args) {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		Map<String, Object> mapTest = new HashMap<String, Object>();
		mapTest.put("id", 123);
		mapTest.put("name", "hello");
		mapTest.put("age", 30);
		mapTest.put("addr", "Seoul Korea");

		String jsonTest = GsonUtils.map2Json(mapTest);
		System.out.println(">>>>> " + jsonTest);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(String.format("\"%s\":\"%s\",", "data1", "value1"));
		sb.append(String.format("\"%s\":\"%s\",", "data2", "value2"));
		sb.append(String.format("\"%s\":\"%s\",", "data3", "value3"));

		jsonTest = jsonTest.replaceFirst("\\{", sb.toString());
		System.out.println(">>>>> " + jsonTest);

		Map<String, Object> mapTest2 = GsonUtils.json2Map(jsonTest);
		//System.out.println(">>>>> " + mapTest2);
		for (Map.Entry<String, Object> entry : mapTest2.entrySet()) {
			String key = String.valueOf(entry.getKey());
			String val = String.valueOf(entry.getValue());
			System.out.printf("[%s] => [%s]%n", key, val);
		}
	}
}
