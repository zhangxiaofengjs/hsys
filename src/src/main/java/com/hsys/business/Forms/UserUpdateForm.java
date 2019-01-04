package com.hsys.business.Forms;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class UserUpdateForm {
	private int id;
	private String field;
	private String value;

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
}
