$(document).ready(function(){
	$("#updateDevice").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"编辑",
			"fields":[
				{
					"id":"no",
					"label":"编号",
					"type":"text",
					"required":true,
				},
				{
					"id":"comment",
					"label":"说明",
					"type":"text",
					"required":true,
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
							response.list.forEach(function(user,index){
								field.options.push(
									{ 
										"text":user.name,
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
	var selIds = htbl.getSelectedRowId("userTable");
	if(selIds.length != 1) {
		return;
	}

	var id = selIds[0];
		"ajax":{
			"url":"/user/json/list",
			"success": function(response, dlg) {
				var field = dlg.field("user.id");
				response.list.forEach(function(user,index){
					field.options.push(
						{ 
							"text":user.name,
							"value":user.id,
						}
					);
				});
				dlg.buildField("user.id");
			},
		},
	
	hdlg.showYesNo(
		"确定重置该用户密码?",
		function() {
			hsys.ajax({
				"url":"/user/json/initpwd",
				"data": {
					"id": id
				},
				"success": function() {
					hsys.success("重置密码为[123]成功，请用户及时修改密码确保安全。", false);
				},
				"error": function(data) {
					hsys.error(data.msg);
				}
			});
		}
	);
});