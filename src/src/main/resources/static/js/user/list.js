$(document).ready(function(){
	$("#addUser").click(function(){
		var self = $(this);

		hdlg.showForm({
			"title":"添加用户",
			"fields":[
				{
					"id":"no",
					"label":"工号",
					"type":"text",
					"required":true,
				},
				{
					"id":"name",
					"label":"姓名",
					"type":"text",
					"required":true,
				},
				{
					"id":"sex",
					"label":"性别",
					"type":"radiogroup",
					"options": [
						{
							"text":'<img alt="男" src="{0}" width=16px>男'.format(hsys.url('/icons/male.png')),
							"value":0
						},
						{
							"text":'<img alt="女" src="{0}" width=16px>女'.format(hsys.url('/icons/female.png')),
							"value":1
						}
					],
					"value": 0
				},
				{
					"id":"password",
					"label":"初始密码",
					"type":"html",
					"value":"123",
				},
			],
			"url":"/user/json/add",
			"success": function(data, dlg) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});

	$("#initPwd").click(function(){
		var self = $(this);
		var selId = htbl.getSelectedRowId("userTable");
		if(selId == null) {
			return;
		}

		hdlg.showYesNo(
			"确定重置该用户密码?",
			function() {
				hsys.ajax({
					"url":"/user/json/initpwd",
					"data": {
						"id": selId
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
});
