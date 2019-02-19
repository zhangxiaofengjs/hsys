package com.hsys.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
public class HsysDate {
	
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	public final static String TIME_PATTERN = "HH:mm:ss";
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    public static Date parse(String strDate, String format) throws ParseException {
    	if(HsysString.isNullOrEmpty(strDate)) {
    		return null;
    	}
    	SimpleDateFormat df = new SimpleDateFormat(format);
        Date date= df.parse(strDate);
        return date;
    }
    
    public static Date parse(String strDate) throws ParseException {
	    return parse(strDate, DATE_TIME_PATTERN);
    }
    
    public static Date tryParse(String date, String fmt) {
    	try {
    		Date d = parse(date, fmt);
    		return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static Date addDay(Date date, int days) {
    	if(date == null) {
    		return null;
    	}
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.add(Calendar.DATE, days);
    	
    	return ca.getTime();
    }

    public static boolean equalsDate(Date date1, Date date2) {
    	if(date1 == null && date2 == null) {
    		return true;
    	} else if(date1 == null || date2 == null) {
    		return false;
    	}
    
    	Date d1 = startOfDay(date1);
    	Date d2 = startOfDay(date2);
    	return d1.compareTo(d2) == 0;
    }
    
	public static Date now() {
		Calendar ca = Calendar.getInstance();
		return ca.getTime();
	}

	public static Date thisMonthStart() {
		Date now = now();
		Calendar ca = Calendar.getInstance();
    	ca.setTime(now);
    	ca.set(Calendar.DAY_OF_MONTH, 1);
    	ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
    	return ca.getTime();
	}

	public static Date endOfDay(Date end) {
		if(end == null) {
			return end;
		}
		Calendar ca = Calendar.getInstance();
    	ca.setTime(end);
    	ca.set(Calendar.HOUR_OF_DAY, 23);
    	ca.set(Calendar.MINUTE, 59);
    	ca.set(Calendar.SECOND, 59);
    	ca.set(Calendar.MILLISECOND, 999);
    	return ca.getTime();
	}

	public static Date startOfDay(Date start) {
		if(start == null) {
			return start;
		}
		Calendar ca = Calendar.getInstance();
    	ca.setTime(start);
    	ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
    	return ca.getTime();
	}
	
	public static Date Today() {
		Calendar ca = Calendar.getInstance();
    	ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/*
	 * 当月工作开始日期,上个月的21日
	 */
	public static Date startOfWorkMonth() {
		Date d = addDay(thisMonthStart(), -1);
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.set(Calendar.DAY_OF_MONTH, 21);
		ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
    	
    	return ca.getTime();
	}

	/*
	 * 本月结束工作日，本月21号
	 */
	public static Date endOfWorkMonth() {
		Date d = startOfDay(now());
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		
		ca.set(Calendar.DAY_OF_MONTH, 20);
		ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
    	
    	return ca.getTime();
	}

	/*
	 * 是否按刻的时间
	 */
	public static boolean isQuarterMinute(Date date) {
		if(date == null) {
			return false;
		}
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		
		return ca.get(Calendar.MINUTE) % 15 == 0;
	}
}
