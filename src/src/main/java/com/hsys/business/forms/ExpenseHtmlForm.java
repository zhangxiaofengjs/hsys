package com.hsys.business.forms;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class ExpenseHtmlForm {
	private String type;
	private String userNo;
	private int receiptId;
	private int id;
	private boolean isFilterUser;
	private boolean isUser;
	private boolean isApproval;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public boolean isApproval() {
		return isApproval;
	}

	public void setApproval(boolean isApproval) {
		this.isApproval = isApproval;
	}

	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}

	public boolean isFilterUser() {
		return isFilterUser;
	}

	public void setFilterUser(boolean isFilterUser) {
		this.isFilterUser = isFilterUser;
	}
}
