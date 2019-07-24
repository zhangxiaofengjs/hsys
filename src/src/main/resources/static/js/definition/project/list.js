$(document).ready(function(){
	$("a[id^=addLeader]").click(function(){
		var self = $(this);
		var selId = self.attr("rowid");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"添加负责者",
			"fields":[
				{
					"id":"projectId",
					"label":"编号",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"userId",
					"label":"负责者",
					"type":"select",
					"options":[],
					"ajax":{
						"url":"/project/json/unleaderList",
						"data": {
							"id": selId
						},
						"success": function(response, dlg) {
							var field = dlg.field("userId");
							field.options.push(
								{ 
									"text": "[请选择负责者]",
									"value": 0,
								}
							);
							response.unleaderList.forEach(function(user,index){
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
			"url":"/project/json/addLeader",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
	
	$("a[id^=deleteLeader]").click(function(){
		var self = $(this);
		var selId0 = self.attr("itemid");
		var selId1 = self.attr("rowid");
		if(selId0 == null) {
			return;
		}
		if(selId1 == null) {
			return;
		}

		hdlg.showYesNo(
			"确定删除选中的负责人吗?",
			function() {
				hsys.ajax({
					"url":"/project/json/deleteLeader",
					"data": {
						"userId": selId0,
						"projectId": selId1
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
	
	$("#addProject").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"添加项目",
			"fields":[
				{
					"id":"no",
					"label":"编号",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"name",
					"label":"项目名",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"funds",
					"label":"经费",
					"type":"text",
					"required":true,
				},
			],
			"url":"/project/json/addProject",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});

	$("#deleteProject").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("projectTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的项目吗?",
			function() {
				hsys.ajax({
					"url":"/project/json/deleteProjects",
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
	
	htbl.rowDoubleClicked("projectTable","updateProject");
	
	$("#updateProject").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("projectTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改项目记录",
			"fields":[
				{
					"id":"id",
					"label":"行号",
					"type":"hidden",
					"value": selId,
				},
				{
					"id":"no",
					"label":"编号",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"name",
					"label":"项目名",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"funds",
					"label":"经费",
					"type":"text",
					"required":true,
				},
			],
			"ajax":{//取得设备信息，更新到dlg上
				"url":"/project/json/getProject",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var project = response.project;
					dlg.buildField("no", project.no);
					dlg.buildField("name", project.name);
					dlg.buildField("funds", project.funds);
				},
			},
			"url":"/project/json/updateProject",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	

});
