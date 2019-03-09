$(document).ready(function(){
	$("#addItem").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"添加报销条目",
			"fields":[
				{
					"id":"date",
					"label":"报销日期",
					"type":"date",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"user.id",
					"label":"报销人",
					"type":"select",
					"options": [],
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("user.id");
							response.users.forEach(function(user,index){
								field.options.push(
									{ 
										"text":"["+ user.no +"]" + user.name,
										"value":user.id,
									}
										
								);
							});
							dlg.buildField("user.id");
						},
					},
				},
				{
					"id":"payee.id",
					"label":"领款人",
					"type":"select",
					"options": [],
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("payee.id");
							response.users.forEach(function(user,index){
								field.options.push(
										{ 
											"text":"["+ user.no +"]" + user.name,
											"value":user.id,
										}
										
								);
							});
							dlg.buildField("payee.id");
						},
					},
				},
				{
					"id":"num",
					"label":"报销金额",
					"type":"number",
					"min":"1",
					"required":true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'加班',
							"value":0,
						},
						{
							"text":'办公用品',
							"value":1,
						},
						{
							"text":'小组活动',
							"value":2,
						},
						{
							"text":'住宿费',
							"value":3,
						},
						{
							"text":'交通费',
							"value":4,
						},
						{
							"text":'电脑设备',
							"value":5,
						},
						{
							"text":'其他',
							"value":6,
						},
					],
					"value":0,
				},
				{
					"id":"comment",
					"label":"事由",
					"type":"text",
					"value":"",
					"required":true,
				},
			],
			"url":"/expense/json/item/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	$("#updateItem").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("itemTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改报销条目",
			"fields":[
				{
					"id":"id",
					"label":"编号",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"date",
					"label":"报销日期",
					"type":"date",
					"required":true,
					"depend": true,
				},
				{
					"id":"userId",
					"label":"报销人",
					"type":"select",
					"options":[],
					"depend": true,
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("userId");
							response.users.forEach(function(user,index){
								field.options.push(
									{ 
										"text":"["+ user.no +"]" + user.name,
										"value":user.id,
									}
								);
							});
							dlg.buildField("userId");
						},
					},
				},
				{
					"id":"payeeId",
					"label":"领款人",
					"type":"select",
					"options":[],
					"depend": true,
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("payeeId");
							response.users.forEach(function(user,index){
								field.options.push(
										{ 
											"text":"["+ user.no +"]" + user.name,
											"value":user.id,
										}
								);
							});
							dlg.buildField("payeeId");
						},
					},
				},
				{
					"id":"num",
					"label":"报销金额",
					"type":"number",
					"min":"1",
					"required":true,
					"depend": true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'加班',
							"value":0,
						},
						{
							"text":'办公用品',
							"value":1,
						},
						{
							"text":'小组活动',
							"value":2,
						},
						{
							"text":'住宿费',
							"value":3,
						},
						{
							"text":'交通费',
							"value":4,
						},
						{
							"text":'电脑设备',
							"value":5,
						},
						{
							"text":'其他',
							"value":6,
						},
					],
					"value":0,
					"depend": true,
				},
				{
					"id":"comment",
					"label":"事由",
					"type":"text",
					"required":true,
					"depend": true,
				},
			],
			"ajax":{//取得信息，更新到dlg上
				"url":"/expense/json/item/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var item = response.item;
					
					dlg.buildField("date", item.date);
					dlg.buildField("userId", item.user.id, false, false);
					dlg.buildField("payeeId", item.payee.id, false, false);
					dlg.buildField("num", item.num);
					dlg.buildField("type", item.type);
					dlg.buildField("comment", item.comment);
				},
			},
			"url":"/expense/json/item/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#deleteItem").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("itemTable", true);
		if(selIds == null) {
			return;
		}
		var id = selIds[0];
		hdlg.showYesNo(
			"确定删除选中的报销条目吗?",
			function() {
				hsys.ajax({
					"url":"/expense/json/item/delete",
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
});

