package com.hsys.business.forms;

/**
 * @author: lingxiaming
 * @version: 2019/05/21
 */
public class CompanyJsonUpdateForm {
	private int id;
	private String name;
	private String address;
	private String phoneNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
