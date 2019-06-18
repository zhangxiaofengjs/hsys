package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Alias("attendanceModel")
public class AttendanceModel extends BaseModel {
	public static final String COND_USER_ID = "userId";
	public static final String COND_DATE = "date";
	public static final String COND_USER_NO = "userNo";
	public static final String COND_START_DATE = "startDate";
	public static final String COND_END_DATE = "endDate";
	public static final String COND_FUZZY_USER_NO = "fuzzyUserNo";
	
	public static final String FIELD_START = "start";
	public static final String FIELD_END = "end";
	public static final String FIELD_COMMENT_START = "commentStart";
	public static final String FIELD_COMMENT_END = "commentEnd";
	
	public static final String ORDER_USER_NO = "userNo";
	public static final String ORDER_DATE = "date";
	
	private Date date;
	private Date start;
	private Date end;
	private UserModel user;
	private String commentStart;
	private String commentEnd;
	
	public String getCommentStart() {
		return commentStart;
	}
	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart;
	}
	public String getCommentEnd() {
		return commentEnd;
	}
	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
}
