package com.hsys.models;

import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Alias("baseModel")
public class BaseModel {
	private int id;
	private UserModel registUser;
	private UserModel updateUser;
	private Date createTime;
	private Date updateTime;
	private HashMap<String, Object> cond = new HashMap<String, Object>();
	private HashMap<String, Object> update = new HashMap<String, Object>();
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserModel getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserModel updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public HashMap<String, Object> getCond() {
		return cond;
	}

	public void setCond(String key, Object value) {
		this.cond.put(key, value);
	}

	public void setUpdate(String fieldKey) {
		this.update.put(fieldKey, true);
	}
	
	public UserModel getRegistUser() {
		return registUser;
	}

	public void setRegistUser(UserModel registUser) {
		this.registUser = registUser;
	}

	public HashMap<String, Object> getUpdate() {
		return update;
	}
}
