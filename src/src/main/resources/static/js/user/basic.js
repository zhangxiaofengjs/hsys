$(document).ready(function(){
	$(":input[name='et_comment']").val($.cookie("_hinfo_userbasic_comment"))
	
	function registExpenseItem() {
		hdlg.showForm({
			"title":"添加一个加班餐报销条目",
			"fields":[
				{
					"id":"date",
					"label":"报销日期",
					"type":"hidden",
					"required":true,
					"value": hdate.format(new Date(),"yyyy-MM-dd"),
				},
				{
					"id":"type",
					"label":"种类",
					"type":"hidden",
					"value": 0, //加班
				},
				{
					"id":"comment",
					"label":"事由",
					"type":"text",
					"value": "加班 " + hdate.format(new Date(),"yyyy-MM-dd"),
					"required":true,
					"maxlength":50,
				},
				{
					"id":"num",
					"label":"报销金额",
					"type":"number",
					"min":"1",
					"value": 30,
					"required":true,
				},
			],
			"buttons":[
				{
					"type": "cancel",
					"text":'不了，不是我付钱的',
					"click": function() { hsys.refresh(); }
				}
			],
			"url":"/expense/json/item/add",
			"success": function(data, dlg) {
				hdlg.showOK("报销记录登记成功!", function() {hsys.refresh();});
			},
			"error": function(data) {
				hdlg.showOK("报销登记发生错误:<br>" + data.msg, function() {hsys.refresh();});
			}
		});
	}
	
	$("#registExtraTime").click(function(){
		var self = $(this);

		self.attr("disabled", true);
		$("#ex_msg").slideUp();

		var startTime = $(":input[name='et_startTime']").val();
		var endTime = $(":input[name='et_endTime']").val();
		var comment = $(":input[name='et_comment']").val();
		var meal = $("#et_meal").val();
		var strMeal = "";
		if(meal == "0") {
			strMeal = "(午餐)";
		} else if(meal == "1") {
			strMeal = "(晚餐)";
		}
		hdlg.showYesNo("确认添加加班记录?<br><span style='font-size:100%;' class='label label-primary'>{0}到{1} {2} {3}</span>".format(startTime, endTime, comment, strMeal), 
			function() { 
				hsys.ajax({
					"url":"/extratime/json/addbyuserbasic",
					"data": {
						"startTime": startTime,
						"endTime": endTime,
						"comment": comment,
						"meal": meal
					},
					"success": function() {
						self.attr("disabled", false);
						$.cookie('_hinfo_userbasic_comment', comment, { expires: 30 });
		
						if(meal != "2") {
							hdlg.showYesNo("<span class='text-danger'>加班记录登记成功!<br></span>顺便添加一个加班餐报销条目？", 
								function() { registExpenseItem();},
								function() { hsys.refresh(); }
							);
						} else {
							hdlg.showOK("加班记录登记成功!", function() {hsys.refresh();});
						}
					},
					"error": function(data) {
						showBasicMsg("ex_msg", "发生错误:<br>" + data.msg, false);
						self.attr("disabled", false);
					}
				});
			},
			function() {
				hsys.refresh();
			}
		);
	});
});

function showBasicMsg(divId, msg, isSuccess) {
	var pDiv = $("#" + divId);
	pDiv.addClass("alert");
	if(isSuccess) {
		pDiv.removeClass("alert-danger");
		pDiv.addClass("alert-success");
	} else {
		pDiv.removeClass("alert-success");
		pDiv.addClass("alert-danger");
	}
	
	pDiv.html(msg);
	
	pDiv.slideDown();
	if(isSuccess) {
		var tm = window.setTimeout(function(){
			pDiv.slideUp();
	    },2000);
	}
}