$(document).ready(function(){
	$("#addGroup").click(function(){
		var self = $(this);

		hdlg.showForm({
			"title":"添加分组信息",
			"fields":[
				{
					"id":"name",
					"label":"组名",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"parentId",
					"label":"上层组织",
					"type":"select",
					"options":[],
					"ajax":{
						"url":"/group/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("parentId");
							field.options.push(
								{ 
									"text": "[无]",
									"value": 0,
								}
							);
							response.groups.forEach(function(group,index){
								field.options.push(
									{ 
										"text":group.name,
										"value":group.id,
									}
								);
							});
							dlg.buildField("parentId");
						},
					},
				},
			],
			"url":"/group/json/add",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#deleteGroup").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("groupTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的组织吗?",
			function() {
				hsys.ajax({
					"url":"/group/json/delete",
					"data": {						
						"ids": selIds
					},
					"success": function() {
						hsys.success(true , getExpandArgs());
					},
					"error": function(data) {
						hsys.error(data.msg);
					}
				});
			}
		);
	});
	
	$("#editGroup").click(function(){
		var self = $(this);
		
		var selId = htbl.getSelectedRowId("groupTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改分组信息",
			"fields":[
				{
					"id":"id",
					"label":"组织id",
					"type":"hidden",
					"required":true,
					"value": selId,
				},
				{
					"id":"name",
					"label":"组织名",
					"type":"text",
					"required":true,
					"depend": true,
					"maxlength":50,
				},
				{
					"id":"parentId",
					"label":"上级组织",
					"type":"select",
					"options":[],
					"depend": true,
					"ajax":{
						"url":"/group/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("parentId");
							
							field.options.push(
								{ 
									"text": "[无]",
									"value": 0,
								}
							);
							response.groups.forEach(function(group,index){
								if(group.id != selId){
									field.options.push(
											{ 
												"text":group.name,
												"value":group.id,
											}
										);
								}
							});
							dlg.buildField("parentId");
						},
					},
				},
			],
			"ajax":{//取得设备信息，更新到dlg上
				"url":"/group/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var group = response.group;
					
					dlg.buildField("name", group.name);
					dlg.buildField("parentId", group.parent != null ? group.parent.id : 0, false, false);
				},
			},
			"url":"/group/json/update",
			"success": function(data, dlg) {
				hsys.success(true , getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});	
});