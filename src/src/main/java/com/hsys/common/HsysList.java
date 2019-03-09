package com.hsys.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.hsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
public class HsysList {
	@SuppressWarnings("hiding")
	public static <T> List<T> New() {
		return new ArrayList<T>();
	}

	@SuppressWarnings("hiding")
	public static <T> T first(List<T> list) {
		if(list == null || list.size() == 0) {
			return null;
		}
		
		return list.get(0);
	}

	@SuppressWarnings("hiding")
	public static <T> boolean isEmpty(List<T> list) {
		if(list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
}
