package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Alias("restModel")
public class RestModel extends BaseModel {
	public static final String COND_USER_NO = "userNo";
	public static final String COND_DATE = "date";
	public static final String COND_ID = "id";
	
	public static final String FIELD_DATE_START = "dateStart";
	public static final String FIELD_DATE_END = "dateEnd";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SUMMARY = "summary";
	public static final String FIELD_LEN = "len";
	
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateStart;
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateEnd;
	private int type;
	private String summary;
	private UserModel user;
	private  int status;
	private UserModel approvalUser;
	private Date approvalDate;
	private int len;
	private Date date;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserModel getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(UserModel approvalUser) {
		this.approvalUser = approvalUser;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
 
}
