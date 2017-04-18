package com.jnzy.mdm.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Property2JsonUtil {

	public static String property2JsonArray(String[] keys,List<String> values) {
		if((values.size()%keys.length)!=0){
			throw new IndexOutOfBoundsException("key-value长度不匹配");
		}
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		int count = values.size()/keys.length;
		
		for(int i=0;i<count;i++){
			HashMap<String,String> map = new HashMap<String,String>();
			for(int j=0;j<keys.length;j++){
				map.put(keys[j], values.get(i*keys.length+j));
			}
			list.add(map);
		}		
		return JSONArray.fromObject(list).toString();
	}
	public static String property2JsonArrayObject(String[] keys,List<Object> values) {
		if((values.size()%keys.length)!=0){
			throw new IndexOutOfBoundsException("key-value长度不匹配");
		}
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		int count = values.size()/keys.length;

		for(int i=0;i<count;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			for(int j=0;j<keys.length;j++){
				map.put(keys[j], values.get(i*keys.length+j));
			}
			list.add(map);
		}
		return JSONArray.fromObject(list).toString();
	}
	
	public static String property2JsonObject(String[] keys,String[] values) {
		if((values.length%keys.length)!=0){
			throw new IndexOutOfBoundsException("key-value长度不匹配");
		}
		
		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<keys.length;i++){
			map.put(keys[i], values[i]);
		}

		return JSONObject.fromObject(map).toString();
	}

	public static JSONObject property2JsonList(String[] keys,String[] values) {
		if((values.length%keys.length)!=0){
			throw new IndexOutOfBoundsException("key-value长度不匹配");
		}

		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<keys.length;i++){
			map.put(keys[i], values[i]);
		}

		return JSONObject.fromObject(map);
	}
}
