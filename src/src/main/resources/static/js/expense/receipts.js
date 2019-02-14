$(document).ready(function(){
	$("#addReceipt").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"登记报销单",
			"fields":[
				{
					"id":"no",
					"label":"编号",
					"type":"text",
					"required":true,
					"value":'#'+ hdate.yyyyMMdd(new Date)+'-',
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'用餐',
							"value":0
						},
						{
							"text":'出租车',
							"value":1
						},
						{
							"text":'支付核销',
							"value":2
						}
					],
					"value": 0
				},
//				{
//					"id":"submitDate",
//					"label":"提交日",
//					"type":"date",
//					"required":true,
//					"value":hdate.yyyy_MM_dd(new Date),
//				},
//				{
//					"id":"payDate",
//					"label":"领款日",
//					"type":"date",
//					"required":true,
//					"value":hdate.yyyy_MM_dd(new Date),
//				},
				{
					"id":"comment",
					"label":"备注",
					"type":"text",
					"required":true,
				},
			],
			"url":"/expense/json/receipt/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
	$("#updateReceipt").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("receiptTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改报销信息",
			"fields":[
				{
					"id":"id",
					"label":"编号",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"no",
					"label":"编号",
					"type":"html",
					"readonly": true,
					"depend": true,//dlg表示时数据不显示处于等待状态
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'用餐',
							"value":0
						},
						{
							"text":'出租车',
							"value":1
						},
						{
							"text":'支付核销',
							"value":2
						}
					],
					"value": 0,
					"depend": true,
				},
				{
					"id":"comment",
					"label":"备注",
					"type":"text",
					"required":true,
					"depend": true,
				},
			],
			"ajax":{//取得信息，更新到dlg上
				"url":"/expense/json/receipt/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var receipt = response.receipt;
					
					dlg.buildField("no", receipt.no);
					dlg.buildField("comment", receipt.comment);
					dlg.buildField("type", receipt.type);
				},
			},
			"url":"/expense/json/receipt/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	$("#deleteReceipt").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("receiptTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的报销单吗?",
			function() {
				hsys.ajax({
					"url":"/expense/json/receipt/delete",
					"data": {
						"ids": selIds
					},
					"success": function() {
						hsys.success(true);
					},
					"error": function(data) {
						hsys.error(data.msg);
					}
				});
			}
		);
	});
	$("#submitReceipt").click(function(){
		var self = $(this);
		var selId = htbl.getSelectedRowId("receiptTable");
		if(selId ==null) {
			return;
		}
		hdlg.showYesNo(
			"确定提交选中的报销单吗?",
			function() {
				hsys.ajax({
					"url":"/expense/json/receipt/submit",
					"data": {
						"id": selId
					},
					"success": function() {
						hsys.success(true);
					},
					"error": function(data) {
						hsys.error(data.msg);
					}
				});
			}
		);
	});
	$("#approvalReceipt").click(function(){
		var self = $(this);
		var selId = htbl.getSelectedRowId("receiptTable");
		if(selId ==null) {
			return;
		}
		hdlg.showYesNo(
				"确定批准选中的报销单吗?",
				function() {
					hsys.ajax({
						"url":"/expense/json/receipt/approval",
						"data": {
							"id": selId
						},
						"success": function() {
							hsys.success(true);
						},
						"error": function(data) {
							hsys.error(data.msg);
						}
					});
				}
		);
	});
	
	$("[id^='downloadReceiptAttachment_'").click(function(){
		var self = $(this);
		var receiptId = self.attr('receiptId');
		
		hdlg.showYesNo(
			"确定下载该订单附件?",
			function() {
				hsys.download({
					"url": "/expense/json/receipt/attachment/download",
					"data": [
						{
							"name": "receiptId",
							"value": receiptId
						},
					]
				});
			}
		);
	});
});
