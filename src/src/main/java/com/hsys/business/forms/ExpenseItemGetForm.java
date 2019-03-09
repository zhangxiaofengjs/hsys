package com.hsys.business.forms;

public class ExpenseItemGetForm {
	private int id;
	private int receiptId;

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptid) {
		this.receiptId = receiptid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
