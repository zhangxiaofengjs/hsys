package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;
import com.hsys.common.HsysTimeDeserializer;
import com.hsys.common.HsysTimeSerializer;

/**
 * @author: 18651304595@163.com
 * @version: 2019/01/28
 */
@Alias("extraTimeModel")
public class ExtraTimeModel extends BaseModel {
	public static final String COND_ID = "id";
	public static final String COND_START_DATE = "startDate";
	public static final String COND_END_DATE = "endDate";
	public static final String COND_USER_NO = "userNo";
	public static final String COND_FUZZY_USER_NO = "fuzzyUserNo";
	public static final String COND_GROUP_ID = "groupId";
	public static final String COND_STATUS = "status";
	public static final String COND_USER_ID = "userId";
	
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_USER_ID = "userId";
	public static final String FIELD_LENGTH = "length";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_START_TIME = "startTime";
	public static final String FIELD_END_TIME = "endTime";
	public static final String FIELD_MEAL_LUNCH = "mealLunch";
	public static final String FIELD_MEAL_SUPPER = "mealSupper";
	public static final String FIELD_TYPE = "type";
	
	public static final String ORDER_USER_NO = "userNo";
	public static final String ORDER_DATE = "date";

	public static final String FIELD_APPROVAL_USER_ID = "approvalUserId";

	public static final String FIELD_APPROVAL_TIME = "approvalTime";
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date date;
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date startTime;
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date endTime;
	private float length;
	private int type;
	private String comment;
	private int mealLunch;
	private int mealSupper;
	private int status;
	private int userId;
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date approvalTime;
	private UserModel user;
	private UserModel appUser;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMealLunch() {
		return mealLunch;
	}
	public void setMealLunch(int mealLunch) {
		this.mealLunch = mealLunch;
	}
	public int getMealSupper() {
		return mealSupper;
	}
	public void setMealSupper(int mealSupper) {
		this.mealSupper = mealSupper;
	}
	public UserModel getAppUser() {
		return appUser;
	}
	public void setAppUser(UserModel appUser) {
		this.appUser = appUser;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	public Date getApprovalTime() {
		return approvalTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
