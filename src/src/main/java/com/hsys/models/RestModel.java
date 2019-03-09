package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateTimeDeserializer;
import com.hsys.common.HsysDateTimeSerializer;

@Alias("restModel")
public class RestModel extends BaseModel {
	public static final String COND_USER_NO = "userNo";
	public static final String COND_DATE = "date";
	public static final String COND_ID = "id";
	public static final String COND_DATE_START ="dateStart";
	public static final String COND_DATE_END ="dateEnd";
	public static final String COND_FUZZY_USER_NO = "fuzzyUserNo"; 
	public static final String COND_STATUS_MULTI = "statusMulti";
	public static final String COND_GROUP_ID = "groupId";
	
	public static final String FIELD_DATE_START = "dateStart";
	public static final String FIELD_DATE_END = "dateEnd";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SUMMARY = "summary";
	public static final String FIELD_LEN = "len";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_APPROVAL_USER_ID = "approvalUserId";
	
	@JsonSerialize(using=HsysDateTimeSerializer.class)
    @JsonDeserialize(using=HsysDateTimeDeserializer.class)
	private Date dateStart;
	@JsonSerialize(using=HsysDateTimeSerializer.class)
    @JsonDeserialize(using=HsysDateTimeDeserializer.class)
	private Date dateEnd;
	private int type;
	private String summary;
	private UserModel user;
	private  int status;
	private UserModel approvalUser;
	private Date approvalDate;
	private float len;
	
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
	public float getLen() {
		return len;
	}
	public void setLen(float len) {
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
