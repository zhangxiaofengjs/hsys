package com.hsys.business.beans;

import java.util.List;

import com.hsys.models.ExpenseItemModel;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.UserModel;

public class ReceiptDetailBean {
	public class UserNumBean {
		private UserModel payee;
		private float num;
		public float getNum() {
			return num;
		}
		public void setNum(float num) {
			this.num = num;
		}
		public UserModel getPayee() {
			return payee;
		}
		public void setPayee(UserModel payee) {
			this.payee = payee;
		}
	}
	private float num;
	private String numStr;
	private boolean isLeader;
	private ExpenseReceiptModel receipt;
	private List<ExpenseItemModel> items;
	private List<UserNumBean> userItems;
	
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public String getNumStr() {
		return numStr;
	}
	public void setNumStr(String numStr) {
		this.numStr = numStr;
	}
	public ExpenseReceiptModel getReceipt() {
		return receipt;
	}
	public void setReceipt(ExpenseReceiptModel receipt) {
		this.receipt = receipt;
	}
	public List<ExpenseItemModel> getItems() {
		return items;
	}
	public void setItems(List<ExpenseItemModel> items) {
		this.items = items;
	}
	public List<UserNumBean> getUserItems() {
		return userItems;
	}
	public void setUserItems(List<UserNumBean> userItems) {
		this.userItems = userItems;
	}
	public boolean isLeader() {
		return isLeader;
	}
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
}
