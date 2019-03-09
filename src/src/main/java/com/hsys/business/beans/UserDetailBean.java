package com.hsys.business.beans;

import com.hsys.models.UserModel;

public class UserDetailBean extends UserModel {
	private String groupFullName;
	
	public String getGroupFullName() {
		return groupFullName;
	}
	public void setGroupFullName(String groupFullName) {
		this.groupFullName = groupFullName;
	}
}
