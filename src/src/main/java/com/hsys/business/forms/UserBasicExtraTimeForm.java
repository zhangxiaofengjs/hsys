package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysTimeDeserializer;
import com.hsys.common.HsysTimeSerializer;

public class UserBasicExtraTimeForm {
	public static final int MEAL_LUNCH = 0;
	public static final int MEAL_SUPPER = 1;
	public static final int MEAL_NONE = 2;
	
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date startTime;
	@JsonSerialize(using=HsysTimeSerializer.class)
    @JsonDeserialize(using=HsysTimeDeserializer.class)
	private Date endTime;
	private String comment;
	private int meal;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getMeal() {
		return meal;
	}
	public void setMeal(int meal) {
		this.meal = meal;
	}
}
