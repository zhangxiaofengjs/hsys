package com.hsys.models;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Alias("projectModel")
public class ProjectModel extends BaseModel {
	public static final String COND_ID = "id";
	public static final String COND_NO = "no";
	public static final String COND_LEADER_ID = "leaderId";
	public static final String COND_PROJECT_ID = "projectId";
	public static final String FIELD_NO = "no";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_FUNDS = "funds";
	private String no;
	private String name;
	private String funds;
	private List<UserModel> users;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<UserModel> getUsers() {
		return users;
	}

	public void setUsers(List<UserModel> users) {
		this.users = users;
	}
	
	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}
}
