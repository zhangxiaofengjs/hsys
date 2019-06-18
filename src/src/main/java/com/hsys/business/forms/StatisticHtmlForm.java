package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

public class StatisticHtmlForm {

	private int id;
	private int sumType;
	private int approveType;
	/*
	 * 用户的参考画面Flag
	 */
	private boolean view;
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date start = HsysDate.startOfWorkMonth();

	@JsonSerialize(using=HsysDateSerializer.class)
	@JsonDeserialize(using=HsysDateDeserializer.class)
	private Date end = HsysDate.endOfWorkMonth();
	
	
	public StatisticHtmlForm() {
		this.sumType = 0;
		this.approveType = 0;
	}
	
	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}
	
	public int getApproveType() {
		return approveType;
	}
	
	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}
	
	public void setSumType(int sumType) {
		this.sumType = sumType;
	}
	
	public int getSumType() {
		return sumType;
	}
	
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
