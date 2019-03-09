package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
public class RestHtmlListForm {
	private String userNo;
	
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date dateStart;

	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date dateEnd;

	private boolean approve;
	private boolean user;
	private boolean view;
	
	public RestHtmlListForm() {
		this.dateStart = HsysDate.startOfWorkMonth();
		this.dateEnd = HsysDate.endOfWorkMonth();
	}
	
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public boolean isUser() {
		return user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}
	
}
