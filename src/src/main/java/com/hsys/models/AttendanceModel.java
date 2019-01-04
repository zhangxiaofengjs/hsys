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
	
	public static final String FIELD_START = "start";
	public static final String FIELD_END = "end";
	public static final String FIELD_COMMENT = "comment";
	
	private Date date;
	private Date start;
	private Date end;
	private String comment;
	private UserModel user;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
