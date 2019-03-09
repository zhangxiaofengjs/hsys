$(document).ready(function(){
	$(":input[name='et_comment']").val($.cookie("_hinfo_userbasic_comment"))
	
	$("#registExtraTime").click(function(){
		var self = $(this);

		self.attr("disabled", true);
		$("#ex_msg").slideUp();

		hsys.ajax({
			"url":"/extratime/json/addbyuserbasic",
			"data": {
				"startTime": $(":input[name='et_startTime']").val(),
				"endTime": $(":input[name='et_endTime']").val(),
				"comment": $(":input[name='et_comment']").val(),
				"meal": $("#et_meal").val()
			},
			"success": function() {
				showBasicMsg("ex_msg", "登记成功!", true);
				self.attr("disabled", false);
				$.cookie('_hinfo_userbasic_comment', $(":input[name='et_comment']").val(), { expires: 30 });
			},
			"error": function(data) {
				showBasicMsg("ex_msg", "发生错误:<br>" + data.msg, false);
				self.attr("disabled", false);
			}
		});
		
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