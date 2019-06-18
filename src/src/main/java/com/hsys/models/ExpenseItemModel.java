package com.hsys.models;

import java.util.Date;
import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsys.common.HsysDateDeserializer;
import com.hsys.common.HsysDateSerializer;

/**
 * @author: qs
 * @version: 2019/1/21
 */
@Alias("expenseItemModel")
public class ExpenseItemModel extends BaseModel {
	public static final String COND_ID = "id";
	public static final String COND_USER_NO = "userNo";
	public static final String COND_USER_OR_PAYEE_NO = "userOrPayeeNo";
	public static final String COND_FUZZY_USER_NO = "fuzzyUserNo";
	public static final String COND_RECEIPT_ID = "receiptId";
	public static final String COND_PAYEE_GROUP_ID = "payeeGroupId";
	public static final String COND_PAYEE_NO = "payeeNo";
	public static final String COND_FUZZY_PAYEE_NO = "fuzzyPayeeNo";
	public static final String COND_STATUS = "status";
	public static final String COND_RECEIPT_NO = "receiptNo";
	
	public static final String FIELD_DATE ="date";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_USER_ID = "userId";
	public static final String FIELD_PAYEE_ID = "payeeId";
	public static final String FIELD_NUM = "num";
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_RECEIPT_ID = "receiptId";
	public static final String FIELD_STATUS = "status";
	
	public static final String ORDER_USER_NO = "userNo";
	public static final String ORDER_DATE = "date";
	
	@JsonSerialize(using=HsysDateSerializer.class)
    @JsonDeserialize(using=HsysDateDeserializer.class)
	private Date date;
	private float num;
	private String comment;
	private int type;
	private ExpenseReceiptModel receipt;
	private UserModel user;
	private UserModel payee;
	private int status;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public UserModel getPayee() {
		return payee;
	}
	public void setPayee(UserModel payee) {
		this.payee = payee;
	}
	public ExpenseReceiptModel getReceipt() {
		return receipt;
	}
	public void setReceipt(ExpenseReceiptModel receipt) {
		this.receipt = receipt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
