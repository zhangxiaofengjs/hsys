package com.hsys.models;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Alias("userModel")
public class UserModel extends BaseModel {
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_PLACE = "place";
	public static final String FIELD_EXIT_DATE = "exitDate";
	public static final String FIELD_ENTER_DATE = "enterDate";
	public static final String FIELD_PHONE_NUMBER = "phoneNumber";
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_ID_NUMBER = "idNumber";
	public static final String FIELD_SCHOOL = "school";
	public static final String FIELD_GRADUATE_DATE = "graduateDate";
	public static final String FIELD_DEGREE = "degree";
	public static final String FIELD_MAJOR = "major";
	public static final String FIELD_MAIL = "mail";
	
	public static final String COND_FUZZY_NO = "fuzzyNo";
	public static final String COND_ID = "id";
	public static final String COND_NO = "no";

	private String no;
	private String name;
	private int sex;
	private String mail;
	private String phoneNumber;
	private String address;
	private String place;
	private String password;
	private String idNumber;
	private String school;
	private String major;
	private int degree;
	private Date graduateDate;
	private Date enterDate;
	private Date exitDate;
	private GroupModel group;
	private List<UserRoleModel> roles;
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public Date getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public List<UserRoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleModel> roles) {
		this.roles = roles;
	}
}
