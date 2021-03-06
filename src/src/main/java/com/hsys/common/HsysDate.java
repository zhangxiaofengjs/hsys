package com.hsys.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.hsys.business.HolidayBusiness;

import org.springframework.beans.factory.annotation.Autowired;

import com.hsys.models.HolidayModel;
import com.hsys.models.enums.HolidayType;
import com.hsys.services.HolidayService;

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
    public static Date addMonth(Date date, int months) {
    	if(date == null) {
    		return null;
    	}
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.add(Calendar.MONTH, months);
    	
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

	public static Date startOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
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
    	return startOfWorkMonth(now());
	}
	
	/*
	 * 当月工作开始日期,上个月的21日
	 */
	public static Date startOfWorkMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		Date d = null;
		if(day > 20) {
			d = ca.getTime();
		} else {
			d = addDay(startOfMonth(date), -1);
		}
		
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
		return endOfWorkMonth(now());
	}
	/*
	 * 本月结束工作日，本月21号
	 */
	public static Date endOfWorkMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		Date d = null;
		if(day > 20) {
			d = addMonth(startOfMonth(date), 1);
		} else {
			d = ca.getTime();
		}

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
	
	public static float hours(Date d1, Date d2) {
		if(d1 == null || d2 == null) {
			return 0;
		}

		float len = (float) ((d1.getTime() - d2.getTime()) / 3600000.0);
		return Math.abs(len);
	}

	public static boolean isWeekend(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		
		switch(ca.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY :
		case Calendar.SATURDAY:
			return true;
		default:
			return false;
		}
	}
	
    private static HolidayService holidayService;
	public static boolean isHoliday(Date date) {
		List<HolidayModel> holidays = holidayService.searchByDate(date);
		boolean a = false;
		for(int i=0;i<holidays.size();i++) {
			if (holidays.get(i).getType()==HolidayType.Rest) {
				a = true;
				break;
			}
		}
		return a;
	}
	public static boolean isNormal(Date date) {
		HolidayModel holidayExist = holidayService.queryByDate(date);
		if (holidayExist.getType()==HolidayType.Work) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public static String getWeekStr(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		
		switch(ca.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY :
			return "日";
		case Calendar.SATURDAY:
			return "六";
		case Calendar.FRIDAY:
			return "五";
		case Calendar.THURSDAY:
			return "四";
		case Calendar.WEDNESDAY:
			return "三";
		case Calendar.TUESDAY:
			return "二";
		case Calendar.MONDAY:
			return "一";
		default:
			return "";
		}
	}

	public static Date getDate(Date date) {
		return startOfDay(date);
	}
}
