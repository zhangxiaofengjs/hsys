package com.hsys.models;

import java.util.Date;

import javax.xml.crypto.Data;

import org.apache.ibatis.type.Alias;

/**
 * @author: qs
 * @version: 2019/1/19
 */
@Alias("expenseReceiptModel")
public class ExpenseReceiptModel extends BaseModel {
	

	public static final String COND_ID = "id";
	public static final String COND_NO = "no";
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_USER_ID = "userId";
	public static final String FIELD_PAYEE_ID = "payeeId";
	private String no;
	private Date submitDate;
	private Date payDate;
	private int type;
	private int payeeId;
	private String comment;
	private int status;
	private int projectId;
	private UserModel user;
	
	

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	
}
