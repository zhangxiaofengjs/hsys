$(document).ready(function(){
	$(":input[name='et_comment']").val($.cookie("_hinfo_userbasic_comment"))
	
	var s = $.cookie("_hinfo_userbasic_start");
	if(s == undefined || s == "") {
		s = "18:00";
	}
	$(":input[name='et_startTime']").val(s);
	
	var e = $.cookie("_hinfo_userbasic_end");
	if(e == undefined || e == "") {
		e = "21:30";
	}
	$(":input[name='et_endTime']").val(e);
	
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
	
	function changeTimeHandler() {
		var startTime = $(":input[name='et_startTime']").val();
		var endTime = $(":input[name='et_endTime']").val();
		var span = hdate.span("2019-04-15 "+ startTime + ":00", "2019-04-15 "+ endTime + ":00", 'h');
		if(span < 2 ) {
			//不是说两小时内不准吃饭，两小时以内一般不填加班餐，所以智能推断下
			$("#et_meal").val(2);
		} else {
			var date = new Date("2019-04-15 "+ startTime + ":00")
			if(date.getHours() > 16) {
				//五点以后开始默认晚餐
				$("#et_meal").val(1);
			} else {
				$("#et_meal").val(0);
			}
		}
	}
	
	$(":input[name='et_startTime']").change(function(){
		changeTimeHandler();
	});
	$(":input[name='et_endTime']").change(function(){
		changeTimeHandler();
	});
	
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
		var span = hdate.span("2019-04-15 "+ startTime + ":00", "2019-04-15 "+ endTime + ":00", 'h');
		hdlg.showYesNo("确认添加加班记录?<br><div style='font-size:100%;' class='alert alert-warning'>{0}到{1} {2}h {3} {4}</div>".
				format(startTime, endTime, span, comment, strMeal), 
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
						$.cookie('_hinfo_userbasic_start', startTime, { expires: 30 });
						$.cookie('_hinfo_userbasic_end', endTime, { expires: 30 });
		
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