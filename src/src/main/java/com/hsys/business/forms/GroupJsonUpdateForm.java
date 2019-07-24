package com.hsys.business.forms;

import com.hsys.models.GroupModel;

public class GroupJsonUpdateForm {
	private int id;
	private String name;
	private GroupModel parent;
	private int level;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GroupModel getParent() {
		return parent;
	}
	
	public void setParent(GroupModel parent) {
		this.parent = parent;
	}
		
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
