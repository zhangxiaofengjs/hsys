$(document).ready(function(){
	$("#addExtra").click(function(){
		var self = $(this);
		var user = $(":hidden[name='user']").val();
		hdlg.showForm({
			"title":"加班登记",
			"fields":[
				{
					"id":"date",
					"label":"加班日期",
					"type":"date",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"startTime",
					"label":"开始时间",
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"18:00"),
				},
				{
					"id":"endTime",
					"label":"结束时间",
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"21:00"),
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'平时',
							"value":0,
						},
						{
							"text":'周末',
							"value":1,
						},
						{
							"text":'节假日',
							"value":2,
						},
						],
						"value":0,
				},
				{
					"id":"length",
					"label":"加班时长",
					"type":"number",
					"min":"0.5",
					"max":"17",
					"value":"3",
					"required":true,
				},
				{
					"id":"comment",
					"label":"事由",
					"type":"text",
					"value":"",
					"required":true,
				},
				{
					"id":"userId",
					"label":"加班人",
					"type": user ? "hidden" : "select",//用户画面无需显示用户
					"options": [],
					"ajax": (user ? undefined : {
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
					}),
					"value": 0,
					"required": true,
				},
				{
					"label":"用餐",
					"id":"meal",
					"type":"checkboxgroup",
					"options":[{
						"value":1,
						"text":"午餐",
					},
					{
						"value":2,
						"text":"晚餐",
					}],
					"value":2
				},
			],
			"url":"/extratime/json/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#updateExtra").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("extraTimeTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改加班记录",
			"fields":[
				{
					"id":"id",
					"label":"编号",
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
					"id":"date",
					"label":"加班日期",
					"type":"date",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"startTime",
					"label":"开始时间",
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"HH:mm"),
				},
				{
					"id":"endTime",
					"label":"结束时间",
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"HH:mm"),
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'平时',
							"value":0,
						},
						{
							"text":'周末',
							"value":1,
						},
						{
							"text":'节假日',
							"value":2,
						},
						],
						"value":0,
				},
				{
					"id":"length",
					"label":"加班时长",
					"type":"number",
					"min":"0.5",
					"max":"17",
					"value":"3",
					"required":true,
				},
				{
					"id":"comment",
					"label":"事由",
					"type":"text",
					"value":"",
					"required":true,
				},
				{
					"label":"用餐",
					"id":"meal",
					"type":"checkboxgroup",
					"options":[{
						"value":1,
						"text":"午餐&nbsp;",
					},
					{
						"value":2,
						"text":"晚餐",
					}],
					"value":2
				},
			],
			"ajax":{//取得设备信息，更新到dlg上
				"url":"/extratime/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var extratime = response.extratime;
					
					var meal = [];
					if(extratime.mealLunch) {
						meal.push(1);
					}
					if(extratime.mealSupper) {
						meal.push(2);
					}
					
					dlg.buildField("date", extratime.date);
					dlg.buildField("startTime", extratime.startTime);
					dlg.buildField("endTime", extratime.endTime);
					dlg.buildField("type", extratime.type);
					dlg.buildField("length", extratime.length);
					dlg.buildField("comment", extratime.comment);
					dlg.buildField("meal", meal);
					dlg.buildField("comment", extratime.comment);
					dlg.buildField("user.name", extratime.user.name, false, false);//需要加载用户一览
				},
			},
			"url":"/extratime/json/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
	
	$("#approvalExtra").click(function(){
		var self = $(this);
		var selId = htbl.getSelectedRowId("extraTimeTable");
		if(selId ==null) {
			return;
		}
		hdlg.showYesNo(
				"确定批准选中的报销单吗?",
				function() {
					hsys.ajax({
						"url":"/extratime/json/approval",
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
	
	$("#deleteExtra").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("extraTimeTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的加班记录吗?",
			function() {
				hsys.ajax({
					"url":"/extratime/json/delete",
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
	
	$("#downloadExtra").click(function(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		
		var self = $(this);
		hdlg.showForm({
			"title":"下载加班记录",
			"fields":[
				{
					"id":"startDate",
					"label":"开始日期",
					"type":"date",
					"required":true,
					"value": startDate
				},
				{
					"id":"endDate",
					"label":"结束日期",
					"type":"date",
					"required":true,
					"value": endDate
				},
				{
					"id":"bySheet",
					"label":"按姓名分页下载",
					"type":"checkbox",
					"required":true,
					"value": true,
					"checked": "checked"
				},
			],
			"url": "/extratime/json/download",
			"isDownload": true,
		});
	});
});
