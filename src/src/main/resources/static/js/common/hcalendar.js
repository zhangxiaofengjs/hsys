function hcalendar() {
};

hcalendar.prototype.init = function(opt) {
	var _this = this;
	
	//opt上的属性全部复制到自己上面
	$.extend(this, opt);
	
	var strHtml = _this.buildCalendarTableHtml();
	
	//追加到亲元素
	var tgtElem = $("#"+ _this.target.safeJqueryId());
	tgtElem.children().remove();
	tgtElem.append(strHtml);
	
	//TODO
	//bindEvent();
}

hcalendar.prototype.buildCalendarTableHtml = function() {
	//TODO
	return '\
		<table class="table table-bordered table-condensed">\
			<tr><td>aaaaa</td><td>bbbbbb</td></tr>\
			<tr><td>aaaaa</td><td>bbbbbb</td></tr>\
		</table>';
}




