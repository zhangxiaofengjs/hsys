package com.hsys.business.forms;

public class UserDownloadForm {
	private String no;

	private boolean view;
	public boolean isView() {
		return view;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return no;
	}
	
	public void setView(boolean view) {
		this.view = view;
	}
	
}
