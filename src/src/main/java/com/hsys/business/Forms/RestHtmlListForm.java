package com.hsys.business.forms;

import java.util.Date;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
public class RestHtmlListForm {
	private String userNo;
	private Date date;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
}
