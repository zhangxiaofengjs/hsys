package com.hsys.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class HsysList {
	public static <T> List<T> New() {
		return new ArrayList<T>();
	}

	public static <T> T first(List<T> list) {
		if(list == null || list.size() == 0) {
			return null;
		}
		
		return list.get(0);
	}
}
