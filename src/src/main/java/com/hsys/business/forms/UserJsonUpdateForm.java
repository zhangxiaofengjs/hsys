package com.hsys.business.forms;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class UserJsonUpdateForm {
	private int id;
	private String field;
	private String value;
	private boolean view;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isView() {
		return view;
	}
	
	public void setView(boolean view) {
		this.view = view;
	}
	
}
