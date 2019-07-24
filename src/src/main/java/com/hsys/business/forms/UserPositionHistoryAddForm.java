package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

public class UserPositionHistoryAddForm {
	
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date date;
	
	private String fromPosition;
	private String toPosition;
	private String comment;
	private int userId;

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setFromPosition(String fromPosition) {
		this.fromPosition = fromPosition;
	}
	
	public String getFromPosition() {
		return fromPosition;
	}
	
	public void setToPosition(String toPosition) {
		this.toPosition = toPosition;
	}
	
	public String getToPosition() {
		return toPosition;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}
}
