package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: qs
 * @version: 2019/1/18
 */
@Alias("deviceModel")
public class DeviceModel extends BaseModel {
	public static final String COND_ID = "id";
	public static final String COND_NO = "no";
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_USER_ID = "userId";
	public static final String COND_USER_NO = "userNo";
	
	private String no;
	private String comment;
	private int status;
	private UserModel user;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
}
