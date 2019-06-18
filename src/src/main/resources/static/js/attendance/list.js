$(function () {
	//翻页初始化
	hpage.init({
		"id":'page',
		"bindFormId": "attForm",
	});

	$("#upload").click(function(){
		var dlg = hdlg.showForm({
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
			"buttons":[
				{
					"type":"ok",
					"click": function() {
						dlg.setFootMsg("<img src='{0}'><span class='hsys-blink' style='background-color:#fff'><b>&nbsp;导入执行中，请等一等...</b></span>".format(hsys.url("/icons/progress-pet.gif")));
					}
				}
			],
			"url":"/attendance/json/upload",
			"success": function(data) {
				hsys.success(true);
			},
			"error": function(data) {
				dlg.setFootMsg("");
				hsys.error(data.msg);
			}
		});
	});
	
	$("#download").click(function(){
		var startDate = $("#start").val();
		var endDate = $("#end").val();
		var userNo = $("#userNo").val();

		var self = $(this);
		var dlg = hdlg.showForm({
			"title":"下载出勤记录",
			"fields":[
				{
					"id":"userNo",
					"label":"工号",
					"type": "text",
					"value": userNo
				},
				{
					"id":"start",
					"label":"开始日期",
					"type":"date",
					"required":true,
					"value": startDate
				},
				{
					"id":"end",
					"label":"结束日期",
					"type":"date",
					"required":true,
					"value": endDate
				},
			],
			"buttons":[
				{
					"type":"ok",
					"click": function() {
						dlg.setFootMsg("<img src='{0}'><span class='hsys-blink' style='background-color:#fff'><b>&nbsp;数据在几秒内会下载完毕，请留意...</b></span>".format(hsys.url("/icons/progress-pet.gif")));
					}
				}
			],
			"url": "/attendance/json/download",
			"isDownload": true,
		});
	});
});