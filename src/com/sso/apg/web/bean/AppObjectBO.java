package com.sso.apg.web.bean;

import java.util.HashMap;

public class AppObjectBO {

	private static HashMap<Object,Object> objectMap = new HashMap<Object, Object>();
	public static final String responseMap = "response";
	public static HashMap<Object,Object> getObjectMap() {
		return objectMap;
	}

	public static void setObjectMap(HashMap<Object,Object> objectMap) {
		AppObjectBO.objectMap = objectMap;
	}
	
	public static void putObject(Object key, Object object){
		objectMap.put(key, object);
	}
}
