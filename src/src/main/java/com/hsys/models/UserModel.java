package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Alias("userModel")
public class UserModel extends BaseModel {
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_NAME = "name";
	
	public static final String COND_FUZZY_NO = "fuzzyNo";

	private String no;
	private String name;
	private int sex;
	private String mail;
	private String phoneNumber;
	private String address;
	private String password;
	private GroupModel group;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}
}
