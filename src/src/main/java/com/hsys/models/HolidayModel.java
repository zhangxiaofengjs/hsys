package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

@Alias("holidayModel")
public class HolidayModel extends BaseModel {
	public static final String COND_DATE_START = "dateStart";
	public static final String COND_DATE_END = "dateEnd";
	
	private int type;
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date date;
	private String comment;
	private int month;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
}
