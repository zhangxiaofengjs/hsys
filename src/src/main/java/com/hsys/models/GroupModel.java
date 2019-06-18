package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Alias("groupModel")
public class GroupModel extends BaseModel {
	public static final String FIELD_NAME = "name";
	public static final String FIELD_PARENT_ID = "parentId";
	public static final String FIELD_LEVEL= "level";
	
	public static final String COND_FUZZY_NAME = "fuzzyName";
	public static final String COND_ID = "id";
	public static final String COND_USER_ID = "userId";
	public static final String COND_PARENT_ID = "parentId";
	public static final String COND_NAME = "name";


	private String name;
	private int level;
	private GroupModel parent;
	private int parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GroupModel getParent() {
		return parent;
	}

	public void setParent(GroupModel parentGroup) {
		this.parent = parentGroup;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
