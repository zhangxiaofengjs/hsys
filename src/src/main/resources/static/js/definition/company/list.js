$(document).ready(function(){
	$("#addCompany").click(function(){
		var self = $(this);

		hdlg.showForm({
			"title":"添加公司信息",
			"fields":[
				{
					"id":"name",
					"label":"公司名",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"phoneNum",
					"label":"电话",
					"type":"text",
					"maxlength":50,
				},
				{
					"id":"address",
					"label":"地址",
					"type":"text",
					"maxlength":50,
				},
			],
			"url":"/company/json/add",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	$("#updateCompany").click(function(){
		var selId = htbl.getSelectedRowId("companyTable");
		if(selId == null) {
			return;
		}
		
		hdlg.showForm({
			"title":"修改公司信息",
			"fields":[
				{
					"id":"id",
					"type":"hidden",
					"value": selId,
				},
				{
					"id":"name",
					"label":"公司名",
					"type":"text",
					"required":true,
					"maxlength":50,
				},
				{
					"id":"phoneNum",
					"label":"电话",
					"type":"text",
					"maxlength":50,
				},
				{
					"id":"address",
					"label":"地址",
					"type":"text",
					"maxlength":50,
				},
			],
			"ajax":{
				"url":"/company/json/get",
				"data": {
					"id": selId
				},
				"success": function(response, dlg) {
					//更新dlg的信息
					var company = response.company;
					dlg.buildField("name", company.name);
					dlg.buildField("phoneNum", company.phoneNum);
					dlg.buildField("address", company.address);
				},
			},
			"url":"/company/json/update",
			"success": function(data, dlg) {
				hsys.success(true, getExpandArgs());
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
	
	htbl.rowDoubleClicked("companyTable","updateCompany");

	$("#deleteCompany").click(function(){
		var self = $(this);
		var selIds = htbl.getSelectedRowId("companyTable", true);
		if(selIds == null) {
			return;
		}

		var id = selIds[0];
		
		hdlg.showYesNo(
			"确定删除选中的公司信息吗?",
			function() {
				hsys.ajax({
					"url":"/company/json/delete",
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
});