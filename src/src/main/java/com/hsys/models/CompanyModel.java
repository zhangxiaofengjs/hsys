package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Alias("companyModel")
public class CompanyModel extends BaseModel {
	
	public static final String COND_NAME = "name";

	public static final String COND_ID = "id";
	
	public static final String FIELD_NAME = "name";
	
	public static final String FIELD_ADDRESS = "address";
	
	public static final String FIELD_PHONENUM = "phoneNum";
	
	/** 会社名. */
	private String name;
	
	/** アドレス. */
	private String address;
	
	/** 電話番号. */
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
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
