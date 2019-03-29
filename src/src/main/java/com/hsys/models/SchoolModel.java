package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Alias("schoolModel")
public class SchoolModel extends BaseModel{
	public static final String COND_NAME = "name";
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
