$(document).ready(function(){
	var ca = new hcalendar();
	ca.init({
		"target":"user_calendar",
		"dateChanged" : function() {
			var html="";
			html = "<div class='{0}'>".format('button')+ "<div>"+"<img name='extra' src='{0}' class='button-img' />".format(hsys.url('/icons/extra.png'))+"<img name='rest' src='{0}' class='button-img' />".format(hsys.url('/icons/rest.png'))+"</div>"+"</div>";
			ca.addAll(html);
			add();
			var holiday = {
		             date:[],
		             type:[],
		             comment:[]
			 };
			$.ajax({
				async: false,
				type:"post",
				url:"/hsys/holiday/json/getHoliday",
				cache:false,
				dataType: "json",
				success:function(res){
					for(var i = 0; i < res.holidays.length ; i++) {
						var  holidays=res.holidays;
						holiday.date.push(holidays[i].date);
						holiday.type.push(holidays[i].type);
						holiday.comment.push(holidays[i].comment);
					    var html = "";
					    		switch(holidays[i].type)
					    		{
					    		case 0:
					    			html ="<div class='{0}'>".format('holiday') + "<img alt='rest' src='{0}'>".format(hsys.url('/icons/holiday.png'))+"<li title='"+holidays[i].comment+"'>" +' &nbsp' + holidays[i].comment + "</li>" + "</div>";
					    			break;
					    		case 1:
					    			html ="<div class='{0}'>".format('holiday') +  "<img alt='change' src='{0}'>".format(hsys.url('/icons/change.png'))+"<li title='"+holidays[i].comment+"'>"+' &nbsp' + holidays[i].comment + "</li>" + "</div>";
					    			break;
					    		case 2:
					    			html ="<div class='{0}'>".format('holiday') +  "<img alt='work' src='{0}'>".format(hsys.url('/icons/work.png'))+"<li title='"+holidays[i].comment+"'>"+' &nbsp' + holidays[i].comment + "</li>" + "</div>";
					    			break;
					    		};
						ca.addItem(holidays[i].date,html);
					 }
				},
					 error: function (errorMsg) {
			             alert("request data failed!!!");
			         }
			});
			
		}
	});
	 
	/**
	 * 添加加班信息
	 * 
	 */ 
	function calcLen(dlg) {
		var start = "2019-04-15 " + dlg.elem("startTime").val() + ":00";
		var end = "2019-04-15 " + dlg.elem("endTime").val() + ":00";
		var s = hdate.span(start, end, "h");
		dlg.elem("length").val(s);
	}
	
	function addExtra(tableDate){
		var self = $(this);
		var user = $(":hidden[name='user']").val();
		hdlg.showForm({
			"title":"加班登记 "+tableDate,
			"fields":[
				{
					"id":"date",
					"label":"加班日期",
					"type":"hidden",
					"required":true,
					"hidden":true,
					"value": tableDate,
				},
				{
					"id":"startTime",
					"label":'<span style="color:red;">实际</span><span>开始时间</span>',
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"18:00"),
					"change": calcLen,
				},
				{
					"id":"endTime",
					"label":'<span style="color:red;">实际</span><span>结束时间</span>',
					"type":"time",
					"required":true,
					"value": hdate.format(new Date(),"21:00"),
					"change": calcLen,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"hidden",
					"options": [
						{
							"text":'平时',
							"value":0,
						},
						{
							"text":'周末',
							"value":1,
						},
						{
							"text":'节假日',
						"value":2,
						},
						],
						"value":0,
						},
						{
							"id":"length",
						"label":"加班时长",
						"type":"number",
						"min":"0.5",
						"max":"17",
						"value":"3",
						"required":true,
						},
						{
							"id":"comment",
						"label":"事由",
						"type":"text",
						"value":"",
						"required":true,
						"maxlength":50,
						},
						{
							"id":"userId",
						"label":"加班人",
						"type": "hidden",//用户画面无需显示用户
						"value": 0,
						"required": true,
						},
						{
							"label":"用餐",
							"id":"meal",
							"type":"checkboxgroup",
							"options":[{
							"value":1,
							"text":"午餐&nbsp;",
							},
							{
							"value":2,
							"text":"晚餐&nbsp;",
							}],
							"value":[2]
						},
						],
						"url":"/extratime/json/add",
						"success": function(data, dlg) {
							hsys.success(true);
						},
						"error": function(data) {
							hsys.error(data.msg);
						}
			});
		}

	/**
	 * 添加请假信息
	 * 	
	 */ 
	function addRest(tableDate){
		var self = $(this);
		var user = $(":hidden[name='user']").val();
		hdlg.showForm({
			
			"title":"请假登记",
			"fields":[
				{
					"id":"userId",
					"label":"申请人",
					"type":"hidden",//用户画面无需显示用户
					"value": 0,
					"required": true,
				},
				{
					"id":"dateStart",
					"label":"开始日期",
					"type":"datetime-local",
					"required":true,
					"value": tableDate + "08:30",
				},
				{
					"id":"dateEnd",
					"label":"结束日期",
					"type":"datetime-local",
					"required":true,
					"value": tableDate + "17:30",
				},
				{
					"id":"len",
					"label":"请假时长（小时）",
					"type":"number",
					"min":"1",
					"value":"8",
					"required":true,
				},
				{
					"id":"type",
					"label":"种类",
					"type":"radiogroup",
					"options": [
						{
							"text":'<span class="label label-primary">休假</span>',
							"value":0,
						},
						{
							"text":'<span class="label label-info">事假</span>',
							"value":2,
						},
						{
							"text":'<span class="label label-warning">病假</span>',
							"value":1,
						},
						{
							"text":'<span class="label label-danger">婚假</span>',
							"value":3,
						},
						{
							"text":'<span class="label label-default">丧假</span>',
							"value":4,
						},
						{
							"text":'<span class="label label-success">公假</span>',
							"value":5,
						},
						],
						"value":0,
						"required":true,
				},
				{
					"id":"summary",
					"label":"备注",
					"type":"textarea",
					"value":"",
					"required":true,
					"maxlength":50,
				},
				],
				"url":"/rest/json/add",
				"success": function(data, dlg) {
					hsys.success(true);
				},
				"error": function(data) {
					hsys.error(data.msg);
				}
			});
		}
	
	/**
	 * 绑定添加按钮
	 */
	function add(){
		$("img[name='extra']").click(function(){
			tableDate=hdate.yyyy_MM_dd(new Date($(this).parent().parent().parent().attr("data")));
			addExtra(tableDate);
		});
		$("img[name='rest']").click(function(){
			tableDate=hdate.yyyy_MM_dd(new Date($(this).parent().parent().parent().attr("data")))+"T";
			addRest(tableDate);
		});
	}
	
	////////////////////////
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