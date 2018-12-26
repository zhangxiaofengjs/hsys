function hsys() {
};

hsys.loginUserId = function() {
	var win = $(window)[0];
	if(win.parent == null) {
		return 0;
	}
    return win.parent.loginUserId();//调用index.js中的函数
}

//取得UrlContext路径
hsys.contextPath = function() {
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    
    return projectName;
}

//取得拼接后Url路径
hsys.url = function(subUrl) {
    return hsys.contextPath() + subUrl;
}

hsys.ajax = function(option) {
	var args = {
    	url : hsys.url(option.url),
        type : 'post',
        dataType : 'json',//接受服务端数据类型
        contentType:"application/json",
        processData: false,
        cache: false,
        data: JSON.stringify(option.data),
        success : function(data) {
        	if(data.success) {
        		if(option.success) {
        			option.success(data);
        		}
        	} else if(option.error) {
        		option.error(data);
        	} else {
        		alert("未定义处理：" + data);
        	}
        },
        error: function(data) {
        	if(!option.disableSysError) {
        		hsys.sysError();
        	}
        }
     };
    
	//提交
	$.ajax(args);
};

hsys.refresh = function(url) {
	if(url == undefined || url == "" || url == null) {
		document.location.reload();
	} else {
		document.location = hsys.url(url);
	}
}

hsys.alert = function(msg) {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg": msg});
}

hsys.success = function(option) {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg":"操作成功。",
		"ok": function() {
			if(option.ok) {
				(option.ok)();
			}
		}});
}

hsys.sysError = function() {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg":"发生系统错误,请联系管理员。"});
}

hsys.download = function(option) {
	var strContent = "";
	if(option.data != null && option.data != undefined) {
		$.each(option.data, function() {
			strContent += '<input type="hidden" name="{0}" value="{1}" />'.format(
					this.name, 
					this.value || '');
		});
	}
	
	var str = '<form action="{0}" method="post">\
		{1}\
		</form>'.format(hsys.url(option.url), strContent);
	
	jQuery(str).appendTo('body').submit().remove();
}

hsys.print = function(option) {
	var target = option;
	if(typeof(option) != "string") {
		target = option.target;
	}
	
	$("#" + target).print({
		title: '上海海隆软件有限公司南通分公司',
	    //Use Global styles
	    globalStyles : true,
	    //Add link with attrbute media=print
	    mediaPrint : false,
	    //Custom stylesheet
	    //stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
	    //Print in a hidden iframe
	    iframe : true,
	    //Don't print this
	    noPrintSelector : ".no-print",
	    //Add this at top
	    prepend : "",
	    //Add this on bottom
	    append : '<div style="text-align:right;color:#9c9a9a;">© 2018-2019  上海海隆软件有限公司南通分公司</div>',
	    //Log to console when printing is done via a deffered callback
	    deferred: $.Deferred().done(function() { console.log('Printing done', arguments); })
	});
}