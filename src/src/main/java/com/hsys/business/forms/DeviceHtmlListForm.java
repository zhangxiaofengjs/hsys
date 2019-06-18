package com.hsys.business.forms;

import com.hsys.HsysSecurityContextHolder;

public class DeviceHtmlListForm {
	private String no;
	
	public DeviceHtmlListForm() {
		this.no = HsysSecurityContextHolder.getLoginUser().getNo();
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

}
