package org.tain.utils;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public final class GsonUtils {

	private static final boolean flag;

	private static final Gson GSON;

	static {
		flag = true;
		GSON = new Gson();
	}

	///////////////////////////////////////////////////////////////////////////

	/*
	 * map from json string
	 */
	public static Map<String, Object> json2Map(String json) {
		return GsonUtils.json2Map(json, false);
	}

	public static Map<String, Object> json2Map(String json, boolean boolGson) {
		if (boolGson) {
			// boolGson = true
			Gson gson = new Gson();
			return gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		} else {
			// boolGson = false
			return GSON.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
		}
	}

	/*
	 * json string from map
	 */
	public static String map2Json(Map<String, Object> map) {
		return GsonUtils.map2Json(map, false);
	}

	public static String map2Json(Map<String, Object> map, boolean boolGson) {
		if (boolGson) {
			// boolGson = true
			Gson gson = new Gson();
			return gson.toJson(map);
		} else {
			// boolGson = false
			return GSON.toJson(map);
		}
	}

	/*
	 * print pretty format
	 */
	public static String toPrettyFormat(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

		return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
	}

	public static String toPrettyFormat(Map<String, Object> map) {
		return GsonUtils.toPrettyFormat(GsonUtils.map2Json(map));
	}

	///////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());
	}
}