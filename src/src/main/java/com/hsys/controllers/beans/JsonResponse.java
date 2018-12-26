package com.hsys.controllers.beans;

import java.util.HashMap;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/03/31
 */
public class JsonResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public JsonResponse() {
		put("success", true);
		put("msg", "success");
	}
	
	public static JsonResponse success(String msg) {
		JsonResponse r = new JsonResponse();
		r.put("success", true);
		r.put("msg", msg);
		return r;
	}
	
	public static JsonResponse success() {
		JsonResponse r = new JsonResponse();
		r.put("success", true);
		return r;
	}
	
	public static JsonResponse error(String msg) {
		JsonResponse r = new JsonResponse();
		r.put("success", false);
		r.put("msg", msg);
		return r;
	}

	public static JsonResponse error() {
		JsonResponse r = new JsonResponse();
		r.put("success", false);
		return r;
	}
	
	public JsonResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public boolean isSuccess() {
		return (Boolean) get("success");
	}
}
