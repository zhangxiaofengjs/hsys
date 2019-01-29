$(function () {
	$("#upload").click(function(){
		hdlg.showForm({
			"title":"导入出勤表",
			"enctype":"multipart/form-data",
			"method":"post",
			"fields":[
				{
					"id":"file",
					"type":"file",
					"label":'选择考勤数据文件',
				}
			],
			"url":"/attendance/json/upload",
			"success": function(data) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
	});
});