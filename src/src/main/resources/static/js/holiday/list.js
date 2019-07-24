$(document).ready(function(){
	$("#addHoliday").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"登记假日",
			"fields":[
				{
					"id":"date",
					"label":"节假日期",
					"type":"date",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"type",
					"label":"类型",
					"type":"radiogroup",
					"options": [
						{
							"text":'休息',
							"value":0
						},
						{
							"text":'出勤',
							"value":1
						}
					],
					"value": 0
				},
				{
					"id":"comment",
					"label":"说明",
					"type":"text",
					"required":true,
					"value": "xx节 国定假日正常休假",
					"maxlength":50,
				},
			],
			"url":"/holiday/json/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	

	$("#updateHoliday").click(function(){
		var self = $(this);

		var selId = htbl.getSelectedRowId("holidayTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改假日信息",
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
					"label":"节假日期",
					"type":"date",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"type",
					"label":"类型",
					"type":"radiogroup",
					"options": [
						{
							"text":'休息',
							"value":0,
						},
						{
							"text":'出勤',
							"value":1,
						},
						],
						"value":0,
				},
				{
					"id":"comment",
					"label":"说明",
					"type":"text",
					"required":true,
					"depend": true,
					"maxlength":50,
				},
				
			],
			"ajax":{
				// 取得假日信息，更新到dlg上
				"url":"/holiday/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					var holiday = response.holiday;
					dlg.buildField("date", holiday.date);
					dlg.buildField("type", holiday.type);
					dlg.buildField("comment", holiday.comment);
				},
			},
			"url":"/holiday/json/update",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
	
	htbl.rowDoubleClicked("holidayTable","updateHoliday");
	
	$("#deleteHoliday").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("holidayTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的假日记录吗?",
			function() {
				hsys.ajax({
					"url":"/holiday/json/delete",
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