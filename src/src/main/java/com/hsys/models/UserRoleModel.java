package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/29
 */
@Alias("userRoleModel")
public class UserRoleModel extends BaseModel {
	public static final String COND_USER_ID = "userId";
	
	private String role;
	private int enable;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
}
