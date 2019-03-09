package com.hsys.models.enums;

public interface ReceiptStatus {
	public  final int Regist = 0;// 编辑中
	public  final int Submit = 1;// 待审核
	public  final int Approval = 2;//处理中
	public  final int Finish = 3;//已领款
}
