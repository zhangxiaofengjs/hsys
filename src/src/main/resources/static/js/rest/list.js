$(document).ready(function(){
	$("#addRest").click(function(){
		var self = $(this);
		
		var user = $(":hidden[name='user']").val();

		hdlg.showForm({
			"title":"添加记录",
			"fields":[
				{
					"id":"userId",
					"label":"申请人",
					"type": user ? "hidden" : "select",//用户画面无需显示用户
					"options": [],
					"ajax": (user ? undefined : {
						"url":"/user/json/list",
						 "success": function(response, dlg) {
							 var field = dlg.field("userId");
							 response.users.forEach(function(user,index){
								 field.options.push(
									 { 
									    "text":"["+user.no+"] "+user.name,
										"value":user.id,
									 }
								 );
							 });
							  dlg.buildField("userId");
						 },
					}),
					"value": 0,
					"required": true,
				},
				{
					"id":"dateStart",
					"label":"开始日期",
					"type":"datetime-local",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-ddT") + "08:30",
				},
				{
					"id":"dateEnd",
					"label":"结束日期",
					"type":"datetime-local",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-ddT") + "17:30",
				},
				{
					"id":"len",
					"label":"请假时长（小时）",
					"type":"number",
					"min":"1",
					"max":"8",
					"value":"8",
					"required":true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'<span class="label label-primary">休假</span>',
							"value":0,
						},
						{
							"text":'<span class="label label-info">事假</span>',
							"value":2,
						},
						{
							"text":'<span class="label label-warning">病假</span>',
							"value":1,
						},
						{
							"text":'<span class="label label-danger">婚假</span>',
							"value":3,
						},
						{
							"text":'<span class="label label-default">丧假</span>',
							"value":4,
						},
						{
							"text":'<span class="label label-success">公假</span>',
							"value":5,
						},
					],
					"value":0,
					"required":true,
				},
				{
					"id":"summary",
					"label":"备注",
					"type":"textarea",
					"value":"",
					"required":true,
				},
			],
			"url":"/rest/json/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#updateRest").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("restTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改请假记录",
			"fields":[
				{
					"id":"id",
					"label":"ID",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"user.name",
					"label":"申请人",
					"type":"html",
					"readonly": true,
					"depend": true,//dlg表示时数据不显示处于等待状态
				},
				{
					"id":"dateStart",
					"label":"开始日期",
					"type":"datetime-local",
					"required":true,
					"depend": true,
				},
				{
					"id":"dateEnd",
					"label":"结束日期",
					"type":"datetime-local",
					"required":true,
					"depend": true,
				},
				{
					"id":"len",
					"label":"请假时长（小时）",
					"type":"number",
					"min":"1",
					"max":"8",
					"required":true,
					"depend": true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'<span class="label label-primary">休假</span>',
							"value":0,
						},
						{
							"text":'<span class="label label-info">事假</span>',
							"value":2,
						},
						{
							"text":'<span class="label label-warning">病假</span>',
							"value":1,
						},
						{
							"text":'<span class="label label-danger">婚假</span>',
							"value":3,
						},
						{
							"text":'<span class="label label-default">丧假</span>',
							"value":4,
						},
						{
							"text":'<span class="label label-success">公假</span>',
							"value":5,
						},
					],
					"value":0,
					"depend": true,
				},
				{
					"id":"summary",
					"label":"备注",
					"type":"textarea",
					"required":true,
					"depend": true,
				},
			],
			"ajax":{//取得请假信息，更新到dlg上
				"url":"/rest/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var rest = response.rest;
					
					dlg.buildField("user.name", rest.user.name);
					dlg.buildField("dateStart", rest.dateStart);
					dlg.buildField("dateEnd", rest.dateEnd);
					dlg.buildField("len", rest.len);
					dlg.buildField("type", rest.type);
					dlg.buildField("summary", rest.summary);
				},
			},
			"url":"/rest/json/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#deleteRest").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("restTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的请假记录吗?",
			function() {
				hsys.ajax({
					"url":"/rest/json/delete",
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
	
	$("#approveRest").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("restTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定批准选中的请假记录吗?",
			function() {
				hsys.ajax({
					"url":"/rest/json/approve",
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
	
	$("#rejectRest").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("restTable", true);
		if(selIds == null) {
			return;
		}
		
		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定驳回选中的请假记录吗?",
			function() {
				hsys.ajax({
					"url":"/rest/json/reject",
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
	
	$("#cancelRest").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("restTable", true);
		if(selIds == null) {
			return;
		}
		
		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定取消选中的请假记录吗?",
			function() {
				hsys.ajax({
					"url":"/rest/json/cancelrequest",
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
