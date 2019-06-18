package com.hsys.business.beans;

import com.hsys.models.HolidayModel;

public class UserBasicBean {
	private String year;
	private String month;
	private String date;
	private String groupName;
	private Float extraTimeTotal;
	private Float restTimeTotal;
	
	private HolidayModel holiday;
	
	public void setRestTimeTotal(Float restTimeTotal) {
		this.restTimeTotal = restTimeTotal;
	}
	public Float getRestTimeTotal() {
		return restTimeTotal;
	}
	
	public void setExtraTimeTotal(Float extraTimeTotal) {
		this.extraTimeTotal = extraTimeTotal;
	}
	
	public Float getExtraTimeTotal() {
		return extraTimeTotal;
	}
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getMonth() {
		return month;
	}
	public HolidayModel getHoliday() {
		return holiday;
	}
	public void setHoliday(HolidayModel holiday) {
		this.holiday = holiday;
	}
	
	
}
