package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;
import com.hsys.common.HsysTimeDeserializer;
import com.hsys.common.HsysTimeSerializer;

public class ExtraTimeAddForm {
	public static final int MEAL_LUNCH = 1;
	public static final int MEAL_SUPPER = 2;
	
	private int[] meal;
	private String comment;
	private int status;
	private int userId;
	private int type;
	private float length;
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date date;
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date startTime;
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date endTime;

	public int[] getMeal() {
		return meal;
	}

	public void setMeal(int[] meal) {
		this.meal = meal;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
