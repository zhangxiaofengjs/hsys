package com.hsys.business.forms;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class UserHtmlListForm {
	private String no;
	private int exitState;
	
	/*
	 * 用户的参考画面Flag
	 */
	private boolean view;

	private int groupId;
	private String groupName;
	
	public UserHtmlListForm() {
		this.exitState = 1;
		this.groupId = 0;
		this.groupName = "--开发组--";
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public int getExitState() {
		return exitState;
	}

	public void setExitState(int exitState) {
		this.exitState = exitState;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
