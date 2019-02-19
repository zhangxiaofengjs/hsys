package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

/**
 * @author: hancaipeng
 * @version: 2019/01/28
 */
public class AttendanceForm {
	private String userNo;
	
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date start = HsysDate.thisMonthStart();

	@JsonSerialize(using=HsysDateSerializer.class)
	@JsonDeserialize(using=HsysDateDeserializer.class)
	private Date end = HsysDate.Today();

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
