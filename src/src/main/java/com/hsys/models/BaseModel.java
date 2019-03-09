package com.hsys.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Alias("baseModel")
public class BaseModel {
	public class OrderCond {
		private String key;
		private String flag;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
	}
	
	private int id;
	private UserModel registUser;
	private UserModel updateUser;
	private Date createTime;
	private Date updateTime;
	private HashMap<String, Object> cond = new HashMap<String, Object>();
	private HashMap<String, Object> update = new HashMap<String, Object>();
	private List<OrderCond> sortOrder = new ArrayList<OrderCond>();
	
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
	
	public void addSortOrder(String key, String value) {
		OrderCond cond = new OrderCond();
		cond.setKey(key);
		cond.setFlag(value);
		this.sortOrder.add(cond);
	}
	
	/*
	 * com.hsys.models.enums.OrderFlag
	 */
	public List<OrderCond> getSortOrder(String key, String flag) {
		return this.sortOrder;
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
	
	public boolean hasUpdate() {
		return getUpdate().size() != 0;
	}
}
