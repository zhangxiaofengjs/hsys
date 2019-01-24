$(document).ready(function(){
	$("#addRest").click(function(){
		var self = $(this);
		hdlg.showForm({
			"title":"添加记录",
			"fields":[
				{
					"id":"user.id",
					"label":"申请人",
					"type":"select",
					"options": [],
				},
				{
					"id":"dateStart",
					"label":"开始日期",
					"type":"date",
					"required":true,
					"value":getNowFormatDate(),
				},
				{
					"id":"dateEnd",
					"label":"结束日期",
					"type":"date",
					"required":true,
					"value":getNowFormatDate(),
				},
//				{
//					"id":"end",
//				    "label":"结束时间",
//					"type":"time",
//					"required":true,
//					"value":"20:30",
//				},
				{
					"id":"len",
					"label":"请假时长（小时）",
					"type":"number",
					"min":"4",
					"max":"8",
					"value":"4",
					"required":true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'休假',
							"value":0,
						},
						{
							"text":'病假',
							"value":1,
						},
						{
							"text":'事假',
							"value":2,
						},
						{
							"text":'婚嫁',
							"value":3,
						},
						{
							"text":'丧假',
							"value":4,
						},
						{
							"text":'公假',
							"value":5,
						},
					],
					"value":0,
				},
				{
					"id":"summary",
					"label":"备注",
					"type":"textarea",
					"value":"",
					"required":true,
				},
			],
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
		var selIds = htbl.getSelectedRowId("userTable");
		if(selIds.length != 1) {
			return;
		}

		var id = selIds[0];
		
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
});

function getNowFormatDate() {
	　　var date = new Date();
	　　var seperator1 = "-";
	　　var year = date.getFullYear();//年
	　　var month = date.getMonth() + 1;//月
	　　var strDate = date.getDate(); //日
	　　if (month >= 1 && month <= 9) {
	　　　　month = "0" + month;
	　　}
	　　if (strDate >= 0 && strDate <= 9) {
	　　　　strDate = "0" + strDate;
	　　}
	　　var currentdate = year + seperator1 + month + seperator1 + strDate;
	　　return currentdate;
	}
