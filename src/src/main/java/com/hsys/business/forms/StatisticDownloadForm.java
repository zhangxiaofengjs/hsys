package com.hsys.business.forms;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;
import com.hsys.models.GroupModel;

public class StatisticDownloadForm {
	public static final int APPROVE_TYPE_NULL = 0;
	public static final int APPROVE_TYPE_NOT_NULL = 1;
	public static final int SUM_TYPE_NULL = 0;
	public static final int SUM_TYPE_NOT_NULL = 1;
	private int sumType;
	private int approveType;
	private GroupModel parent;
	private int id;
	private String name;
	public static final String COND_ID = "id";
	private String userNo;
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date endDate;
	
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date startDate;

	
	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}
	
	public int getApproveType() {
		return approveType;
	}
	

	public void setSumType(int sumType) {
		this.sumType = sumType;
	}
	
	public int getSumType() {
		return sumType;
	}
 	
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public GroupModel getParent() {
		return parent;
	}
	
	public void setParent(GroupModel parent) {
		this.parent = parent;
	}

	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
}
