package com.hsys.common;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
public class HsysString {
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	public static String trim(String str, String trim) {
		if(isNullOrEmpty(str)) {
			return str;
		}
		
		if(str.startsWith(trim)) {
			str = str.substring(trim.length());
		}
	
		if(str.endsWith(trim)) {
			str = str.substring(0, str.length() - trim.length());
		}
		
		return str;
	}
}
