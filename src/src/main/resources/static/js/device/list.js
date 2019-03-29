$(document).ready(function(){
	$("#addDevice").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"登记设备",
			"fields":[
				{
					"id":"no",
					"label":"编号",
					"type":"text",
					"required":true,
					"maxlength":7,
				},
				{
					"id":"comment",
					"label":"说明",
					"type":"text",
					"required":true,
					"value": "开发用PC",
					"maxlength":50,
				},
				{
					"id":"status",
					"label":"状态",
					"type":"radiogroup",
					"options": [
						{
							"text":'正常',
							"value":0
						},
						{
							"text":'损坏',
							"value":1
						},
						{
							"text":'交还',
							"value":2
						}
					],
					"value": 0
				},
				{
					"id":"user.id",
					"label":"使用者",
					"type":"select",
					"options":[],
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("user.id");
							field.options.push(
								{ 
									"text": "[无使用者]",
									"value": 0,
								}
							);
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
			],
			"url":"/device/json/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	

	$("#updateDevice").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("deviceTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改设备信息",
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
					"id":"comment",
					"label":"说明",
					"type":"text",
					"required":true,
					"depend": true,
					"maxlength":50,
				},
				{
					"id":"status",
					"label":"状态",
					"type":"radiogroup",
					"options": [
						{
							"text":'正常',
							"value":0
						},
						{
							"text":'损坏',
							"value":1
						},
						{
							"text":'交还',
							"value":2
						}
					],
					"value": 0,
					"depend": true,
				},
				{
					"id":"userId",
					"label":"使用者",
					"type":"select",
					"options":[],
					"depend": true,
					"ajax":{
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("userId");
							
							field.options.push(
								{ 
									"text": "[无使用者]",
									"value": 0,
								}
							);
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
			],
			"ajax":{//取得设备信息，更新到dlg上
				"url":"/device/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var device = response.device;
					
					dlg.buildField("no", device.no);
					dlg.buildField("comment", device.comment);
					dlg.buildField("status", device.status);
					dlg.buildField("userId", device.user != null ? device.user.id : 0, false, false);//需要加载用户一览
				},
			},
			"url":"/device/json/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
	
	$("#deleteDevice").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("deviceTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的机器设备吗?",
			function() {
				hsys.ajax({
					"url":"/device/json/delete",
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