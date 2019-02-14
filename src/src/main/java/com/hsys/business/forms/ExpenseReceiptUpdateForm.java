package com.hsys.business.forms;

public class ExpenseReceiptUpdateForm {
	private int id;
	private String no;
	private int status;
	private int type;
	private int payeeId;
	private String comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUserId() {
		return payeeId;
	}
	public void setUserId(int userId) {
		this.payeeId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
