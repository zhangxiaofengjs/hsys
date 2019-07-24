$(document).ready(function(){
	$("#submitReceipt").click(function(){
		var self = $(this);
		hdlg.showYesNo(
			"确定提交选中的报销单吗?",
			function() {
				hsys.ajax({
					"url":"/expense/json/receipt/submit",
					"data": {
						"id": $("#receiptId").val()
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
		hdlg.showYesNo(
				"确定批准报销单吗?",
				function() {
					hsys.ajax({
						"url":"/expense/json/receipt/approval",
						"data": {
							"id": $("#receiptId").val()
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
	$("#rejectReceipt").click(function(){
		var self = $(this);
		hdlg.showYesNo(
				"确定驳回报销单吗?",
				function() {
					hsys.ajax({
						"url":"/expense/json/receipt/reject",
						"data": {
							"id": $("#receiptId").val()
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
	$("#finishReceipt").click(function(){
		var self = $(this);
		hdlg.showYesNo(
				"确定标记的报销单已经领款吗?",
				function() {
					hsys.ajax({
						"url":"/expense/json/receipt/finish",
						"data": {
							"id": $("#receiptId").val()
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
	$("#linkItem").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"关联报销条目",
			"fields":[
				{
					"id":"receiptId",
					"label":"编号",
					"required":true,
					"type":"hidden",
					"value": $("#receiptId").val(),
				},
				{
					"id":"id",
					"label":"报销单详细",
					"type":"select",
					"options":[],
					"required":true,
					"ajax":{
						"url":"/expense/json/item/getunlinked",
						"success": function(response, dlg) {
							var field = dlg.field("id");
							response.items.forEach(function(item,index){
								field.options.push(
									{ 
										"text":"["+ item.date +"]" + " 报销人  " + item.user.no + " " + item.user.name + " 报销金额  " +item.num,
										"value":item.id,
									}
								);
							});
							dlg.buildField("id");
						},
					},
				},
			],
			"url":"/expense/json/item/link",
			"success": function(data, dlg) {
				hsys.refresh();
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#unlinkItem").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("itemTable", true);
		if(selIds == null) {
			return;
		}
		var id = selIds[0];
		hdlg.showYesNo(
			"确定解除关联选中的报销条目吗?",
			function() {
				hsys.ajax({
					"url":"/expense/json/item/unlink",
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
	
	$("#setProject").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"设定报销项目编号",
			"fields":[
				{
					"id":"id",
					"label":"编号",
					"required":true,
					"type":"hidden",
					"value": $("#receiptId").val(),
				},
				{
					"id":"projectId",
					"label":"报销项目编号",
					"type":"select",
					"options":[],
					"ajax":{
						"url":"/project/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("projectId");
							response.projects.forEach(function(project,index){
								field.options.push(
									{ 
										"text":"[" + project.no + "] " + project.name,
										"value":project.id,
									}
								);
							});
							dlg.buildField("projectId");
						},
					},
				},
			],
			"url":"/expense/json/receipt/setproject",
			"success": function(data, dlg) {
				hsys.refresh();
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#setComment").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"设定备注",
			"fields":[
				{
					"id":"id",
					"label":"编号",
					"required":true,
					"type":"hidden",
					"value": $("#receiptId").val(),
				},
				{
					"id":"comment",
					"label":"备注",
					"type":"text",
					"value": $("#r_comment").text(),
				},
			],
			"url":"/expense/json/receipt/setcomment",
			"success": function(data, dlg) {
				hsys.refresh();
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
});