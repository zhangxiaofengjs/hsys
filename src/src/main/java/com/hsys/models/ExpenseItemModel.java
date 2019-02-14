package com.hsys.models;

import java.util.Date;
import org.apache.ibatis.type.Alias;

/**
 * @author: qs
 * @version: 2019/1/21
 */
@Alias("expenseItemModel")
public class ExpenseItemModel extends BaseModel {
	private Date date;
	private int userId;
	private int payeeId;
	private float num;
	private String comment;
	private int type;
	private int receiptId;
	private UserModel user;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
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
	public int getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
}
