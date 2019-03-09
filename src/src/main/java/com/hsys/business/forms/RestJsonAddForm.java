package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateTimeDeserializer;
import com.hsys.common.HsysDateTimeSerializer;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/20
 */
public class RestJsonAddForm {
	@JsonSerialize(using=HsysDateTimeSerializer.class)
    @JsonDeserialize(using=HsysDateTimeDeserializer.class)
	private Date dateStart;
	@JsonSerialize(using=HsysDateTimeSerializer.class)
    @JsonDeserialize(using=HsysDateTimeDeserializer.class)
	private Date dateEnd;
	private int type;
	private String summary;
	private int userId;
	private int len;

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
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
