function getExpandArgs() {
	return {
		"schoolExpand" : $("#schoolTableExpand").val(),
		"groupExpand" : $("#groupTableExpand").val(),
		"companyExpand" : $("#companyTableExpand").val(),
		"projectExpand" : $("#projectTableExpand").val(),
	}
}

$(document).ready(function(){
	//各个定义的打开关闭状态
	$("div[id$=TableDiv]").on("shown.bs.collapse", function(){
		$(this).children().first().val(true);
		
		var win = $(window)[0];
		if(win.parent != null) {
			win.parent.resizeWinHeight();//调用index.js中的函数,重新設定win大小
		}
	});
	$("div[id$=TableDiv]").on("hidden.bs.collapse", function(){
		$(this).children().first().val(false);
	});
});
