package com.hsys.models.enums;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/18
 */
public interface RestStatus {
	public  final int Regist = 0;// 登录中
	public  final int Approval = 1;//批准
	public  final int Reject = 2;//驳回
	public  final int CancelRequest = 3;//撤销请求
	public  final int Cancel = 4;//撤销
}
