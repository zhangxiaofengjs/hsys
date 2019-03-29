package com.hsys.business.beans;

public class UserBasicBean {
	private String year;
	private String month;
	private String date;
	private String groupName;
	private Float extraTimeTotal;
	private Float restTimeTotal;
	
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
	
	
}
