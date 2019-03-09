package com.hsys.models;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @author: qs
 * @version: 2019/1/19
 */
@Alias("expenseReceiptModel")
public class ExpenseReceiptModel extends BaseModel {
	public static final String COND_PAYEE_NO = "payeeNo";
	public static final String COND_ID = "id";
	public static final String COND_NO = "no";
	public static final String COND_FUZZY_PAYEE_NO = "fuzzyPayeeNo";
	public static final String COND_PAYEE_GROUP_ID = "payeeGroupId";
	public static final String COND_STATUS = "status";
	public static final String COND_PROJECT_IDS = "projectIds";
	
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_PAYEE_ID = "payeeId";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PROJECT_ID = "projectId";
	public static final String FIELD_PAY_DAYE = "payDate";

	private String no;
	private Date submitDate;
	private Date payDate;
	private int type;
	private UserModel payee;
	private String comment;
	private String attachmentPath;
	private int status;
	private ProjectModel project;
	
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

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public UserModel getPayee() {
		return payee;
	}

	public void setPayee(UserModel payee) {
		this.payee = payee;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}
}
