package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Alias("groupModel")
public class GroupModel extends BaseModel {
	public static final String FIELD_NAME = "name";
	
	public static final String COND_FUZZY_NAME = "fuzzyName";
	public static final String COND_USER_ID = "userId";

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}