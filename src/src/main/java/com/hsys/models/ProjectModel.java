package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Alias("projectModel")
public class ProjectModel extends BaseModel {
	public static final String COND_ID = "id";
	public static final String COND_LEADER_ID = "leaderId";
	private String no;
	private String name;

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
}
