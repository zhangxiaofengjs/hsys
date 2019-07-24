$(document).ready(function(){
	$("#schoolAdd").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"添加学校",
			"fields":[
				
				{
					"id":"name",
					"label":"名称",
					"type":"text",
					"required":true,
					"maxlength":8,
				},
			],
			"url":"/school/json/add",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});

	$("#schoolDelete").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("schoolTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的学校吗?",
			function() {
				hsys.ajax({
					"url":"/school/json/delete",
					"data": {
						"ids": selIds
					},
					"success": function() {
						hsys.success(true, getExpandArgs());
					},
					"error": function(data) {
						hsys.error(data.msg);
					}
				});
			}
		);
	});
	
	htbl.rowDoubleClicked("schoolTable","schoolEdit");
	
	$("#schoolEdit").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("schoolTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改学校数据记录",
			"fields":[
				{
					"id":"id",
					"label":"行号",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"name",
					"label":"名称",
					"type":"text",
					"value":"",
					"required":true,
					"maxlength":50,
				},
			],
			"ajax":{//取得设备信息，更新到dlg上
				"url":"/school/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var school = response.school;
					dlg.buildField("name", school.name);
				},
			},
			"url":"/school/json/update",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	

});
