package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: lingxiaming
 * @version: 2019/06/25
 */
@Alias("userPositionHistoryModel")
public class UserPositionHistoryModel extends BaseModel {

	public static final String COND_ID = "id";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_FROM_POSITION = "fromPosition";
	public static final String FIELD_TO_POSITION = "toPosition";
	public static final String FIELD_COMMENT = "comment";
	private int id;
	private Date date;
	private String fromPosition;
	private String toPosition;
	private String comment;
	private int userId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFromPosition() {
		return fromPosition;
	}
	
	public void setFromPosition(String fromPosition) {
		this.fromPosition = fromPosition;
	}
	
	public String getToPosition() {
		return toPosition;
	}
	
	public void setToPosition(String toPosition) {
		this.toPosition = toPosition;
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
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}
}
