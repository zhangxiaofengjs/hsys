package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: zhangyanhong@hyron.com
 * @version: 2019/04/02
 */
@Alias("statisticModel")
public class StatisticModel  extends BaseModel{
	
	public static final String COND_USER_NO = "userNo";
	public static final String COND_USER_NAME = "userName";
	public static final String COND_USER_ID = "userId";
	public static final String COND_START_DATE = "startDate";
	public static final String COND_END_DATE = "endDate";
	public static final String COND_DATE_START ="dateStart";
	public static final String COND_DATE_END ="dateEnd";
	public static final String COND_SUM_TYPE_NULL = "SumTypeNull";
	public static final String COND_SUM_TYPE_NOT_NULL = "SumTypeNotNull";
	public static final String COND_APPROVE_TYPE_NULL="approveTypeNull";
	public static final String COND_APPROVE_TYPE_NOT_NULL="approveTypeNotNull";
	public static final String COND_STATUS ="status";
	public static final String COND_EXIT_DATE_NULL = "exitDateNull";
	public static final String COND_EXIT_DATE_NOT_NULL = "exitDateNotNull";
	public static final String COND_HIDDEN_FALG = "hiddenFlag";
	
	private UserModel user;
	private float etNormal;
	private float etWeekend;
	private float etHoliday;
	private float etSum;
	private float rtVacation;
	private float rtSick;
	private float rtLeave;
	private float rtMarriage;
	private float rtFuneral;
	private float rtPublics;
	private float rtSum;
	private String groupName;
	private String userNo;
	private String userName;
	private Date exitDate;
	
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}
	
	public Date getExitDate() {
		return exitDate;
	}
	
	public float getRtSum() {
		return rtSum;
	}
	
	public void setRtSum(float rtSum) {
		this.rtSum = rtSum;
	}
	
	public float getRtVacation() {
		return rtVacation;
	}

	public void setRtVacation(float rtVacation) {
		this.rtVacation = rtVacation;
	}

	public float getRtSick() {
		return rtSick;
	}

	public void setRtSick(float rtSick) {
		this.rtSick = rtSick;
	}

	public float getRtLeave() {
		return rtLeave;
	}

	public void setRtLeave(float rtLeave) {
		this.rtLeave = rtLeave;
	}

	public float getRtMarriage() {
		return rtMarriage;
	}

	public void setRtMarriage(float rtMarriage) {
		this.rtMarriage = rtMarriage;
	}

	public float getRtFuneral() {
		return rtFuneral;
	}

	public void setRtFuneral(float rtFuneral) {
		this.rtFuneral = rtFuneral;
	}

	public float getRtPublics() {
		return rtPublics;
	}

	public void setRtPublics(float rtPublics) {
		this.rtPublics = rtPublics;
	}


	
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getEtNormal() {
		return etNormal;
	}

	public void setEtNormal(float etNormal) {
		this.etNormal = etNormal;
	}

	public float getEtWeekend() {
		return etWeekend;
	}

	public void setEtWeekend(float etWeekend) {
		this.etWeekend = etWeekend;
	}

	public float getEtHoliday() {
		return etHoliday;
	}

	public void setEtHoliday(float etHoliday) {
		this.etHoliday = etHoliday;
	}

	public float getEtSum() {
		return etSum;
	}

	public void setEtSum(float etSum) {
		this.etSum = etSum;
	}
	
	

}
