package com.hsys.common;

import com.hsys.exception.HsysException;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
public class HsysString {
	private static String[] bigLetter = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private static String[] unit = { "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万" };
	private static String[] small = { "分", "角" };

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

	public static int len(String str) {
		if(str == null) {
			return 0;
		}
		return str.length();
	}
	
	public static String getRMBStr(float num) {
		if (num <= 0) {
			return "零圆整";
		}

		String s = String.valueOf(num);
		
		s = roundString(s);
		int index = s.indexOf(".");
		String intOnly = s.substring(0, index);
		String part1 = numFormat(1, intOnly); // 整数部分转换

		String smallOnly = s.substring(index + 1);
		String part2 = "";
		if ("00".equals(smallOnly)) {
			part2 = "整";
		} else {
			part2 = numFormat(2, smallOnly); // 小数部分转换
		}

		String newS = part1 + part2;
		newS = cleanZero(newS);
		return newS;
	}

	private static String roundString(String s) {
		if ("".equals(s)) {
			return "";
		}
		double d = Double.parseDouble(s);
		d = (d * 100 + 0.5) / 100;
		s = new java.text.DecimalFormat("##0.000").format(d);
		int index = s.indexOf(".");
		String intOnly = s.substring(0, index);
		if (intOnly.length() > 13) {
			throw new HsysException("输入数据过大，整数部分最多13位！");
		}
		String smallOnly = s.substring(index + 1);
		if (smallOnly.length() > 2) {
			String roundSmall = smallOnly.substring(0, 2);
			s = intOnly + "." + roundSmall;
		}
		return s;
	}

	private static String numFormat(int flag, String s) {
		int sLength = s.length();
		String newS = "";
		for (int i = 0; i < sLength; i++) {
			if (flag == 1) {
				newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];
			} else if (flag == 2) {
				newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];
			}
		}

		return newS;
	}

	private static String cleanZero(String s) {
		if ("".equals(s)) {
			return "";
		}
		while (s.charAt(0) == '零') {
			s = s.substring(2);
			if (s.length() == 0) {
				return "零";
			}
		}
		String regex1[] = { "零仟", "零佰", "零拾" };
		String regex2[] = { "零亿", "零万", "零元", "零整" };
		String regex3[] = { "亿", "万", "元", "元整" };
		String regex4[] = { "零角", "零分" };
		for (int i = 0; i < 3; i++) {
			s = s.replaceAll(regex1[i], "零");
		}
		for (int i = 0; i < 4; i++) {
			s = s.replaceAll("零零零", "零");
			s = s.replaceAll("零零", "零");
			s = s.replaceAll(regex2[i], regex3[i]);
		}
		for (int i = 0; i < 2; i++) {
			s = s.replaceAll(regex4[i], "");
		}
		s = s.replaceAll("亿万", "亿");
		return s;

	}
}
