package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Alias("schoolModel")
public class SchoolModel extends BaseModel{
	public static final String COND_NAME = "name";

	public static final String COND_ID = "id";

	public static final String FIELD_NAME = "name";

	public static final String FIELD_PROVINCE = "province";
	
	private String name;
	private String no;
	private String province;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return no;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getProvince() {
		return province;
	}
}
