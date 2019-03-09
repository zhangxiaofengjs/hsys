package com.hsys.business.forms;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class UserHtmlListForm {
	private String no;
	private int exitState;
	private boolean view;

	public UserHtmlListForm() {
		this.exitState = 1;
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
}
