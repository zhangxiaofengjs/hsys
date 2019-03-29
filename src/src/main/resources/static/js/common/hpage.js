function hpage() {
};

hpage.init = function(opt) {
	var p = new hpage();
	p.build(opt);
	return p;
};

hpage.prototype.build = function(opt) {
	var _this = this;
	
	//opt上的属性全部复制到自己上面
	$.extend(this, opt);

	_this.pages = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pages")).val());
	_this.pageNum = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pageNum")).val());
	_this.pageStart = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pageStart")).val());
	_this.pageEnd = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pageEnd")).val());
	_this.pageCount = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pageCount")).val());
	_this.pageSize = parseInt($('#{0} input[name="{1}"]'.format(_this.id, "pageSize")).val());

	var strHtml = '<div style="display:inline;" id="hpage_{0}">'.format(_this.id);
	strHtml += '&nbsp;<button type="button" style="line-height:1.0;" class="btn btn-sm btn-default {0}" id="hpage_btn_{1}_prevpages">&laquo;</button>&nbsp;'.
		format(	_this.pageStart == 1 ? "disabled" : '',
				_this.id);
	
	for(var i = _this.pageStart; i <= _this.pageEnd; i++) {
		strHtml += '<button type="button" style="line-height:1.0;" class="btn btn-sm {0}" id="hpage_btn_{1}_page_{2}" page={2}>{2}</button>&nbsp;'.
			format(i == _this.pageNum ? 'btn-primary' : 'btn-default', _this.id, i);
	}
	strHtml += '<button type="button" style="line-height:1.0;" class="btn btn-sm btn-default {0}" id="hpage_btn_{1}_nextpages">&raquo;</button>&nbsp;'.format(
		_this.pageEnd == _this.pages ? "disabled" : '',
		_this.id
	);;
		    
	strHtml += '<input style="width:30px;font-size:12px;" id="hpage_input_{1}_pagesize" value="{0}">条/页&nbsp;'.format(_this.pageSize, _this.id);
	strHtml += '&nbsp;{0}页/{1}页&nbsp;'.format(_this.pageNum, _this.pages);
	strHtml += '<input style="width:26px;font-size:12px;" id="hpage_input_{1}_page" value="{0}">&nbsp;'.format(_this.pageNum, _this.id);
	strHtml += '<button type="button" style="line-height:1.0;" class="btn btn-sm btn-primary" id="hpage_btn_{0}_jump">跳转</button>'.format(_this.id);
	strHtml += '</div>';
	
	var target = $('#'+ _this.id);
	target.append(strHtml);
	
	//加载事件
	$("#hpage_{0} [id=hpage_btn_{0}_prevpages]".format(_this.id)).click(function(pBtn){
		_this.gotoPrevPages();
	});
	
	$("#hpage_{0} [id=hpage_btn_{0}_nextpages]".format(_this.id)).click(function(){
		_this.gotoNextPages();
	});
	
	$("#hpage_{0} [id^=hpage_btn_{0}_page_]".format(_this.id)).click(function(){
		_this.clickPage(this);
	});
	
	$("#hpage_{0} [id=hpage_input_{0}_pagesize]".format(_this.id)).keydown(function (e){
		if(e.which == "13"){
			//enter
			_this.refreshPageSize();
	  	}
	})
	
	$("#hpage_{0} [id=hpage_input_{0}_page]".format(_this.id)).keydown(function (e){
		if(e.which == "13"){
			//enter
			_this.gotoPage();
	  	}
	})
	
	$("#hpage_{0} [id=hpage_btn_{0}_jump]".format(_this.id)).click(function (e){
		_this.gotoPage();
	})
}

hpage.prototype.gotoPrevPages = function() {
	var _this = this;
	if(_this.pageStart == 1) {
		return;
	}
	
	_this.updatePrevPages();
	
	_this.pageNum = _this.pageStart;
	_this.goto();
}

hpage.prototype.updatePrevPages = function() {
	var _this = this;
	var pageStart = _this.pageStart - _this.pageCount;
	if(pageStart < 1) {
		pageStart = 1;
	}
	_this.pageStart = pageStart;
	
	var pageEnd = _this.pageStart + _this.pageCount - 1;
	if(pageEnd > _this.pages) {
		_this.pageEnd = _this.pages;
	} else {
		_this.pageEnd = pageEnd;
	}
}

hpage.prototype.gotoNextPages = function() {
	var _this = this;
	if(_this.pageEnd == _this.pages) {
		return;
	}
	
	_this.updateNextPages();

	_this.pageNum = _this.pageStart;
	_this.goto();
}

hpage.prototype.updateNextPages = function() {
	var _this = this;
	
	var pageStart = _this.pageEnd + 1;
	if(pageStart > _this.pages) {
		return;
	}
	_this.pageStart = pageStart;
	
	var pageEnd = _this.pageStart + _this.pageCount - 1;
	if(pageEnd > _this.pages) {
		_this.pageEnd = _this.pages;
	} else {
		_this.pageEnd = pageEnd;
	}
}

hpage.prototype.clickPage = function(pBtn) {
	var _this = this;
	var btn = $(pBtn);
	
	_this.pageNum = btn.attr("page");
	_this.goto();
}

hpage.prototype.refreshPageSize = function() {
	var _this = this;
	var pElem = $("#hpage_{0} [id=hpage_input_{0}_pagesize]".format(_this.id));
	
	_this.pageSize = parseInt(pElem.val());
	_this.goto();
}

hpage.prototype.gotoPage = function() {
	var _this = this;
	var pElem = $("#hpage_{0} [id=hpage_input_{0}_page]".format(_this.id));
	
	var pageNum = parseInt(pElem.val());
	if(pageNum < 1 || pageNum > _this.pageSize) {
		return;
	}
	
	while(pageNum > _this.pageEnd) {
		//超过当前表示页面
		_this.updateNextPages();
	}
	
	while(pageNum < _this.pageStart) {
		//超过当前表示页面
		_this.updatePrevPages();
	}
	
	_this.pageNum = pageNum;
	_this.goto();
}

hpage.prototype.goto = function() {
	var _this = this;
	
	_this.createPageElem("pageStart", _this.pageStart);
	_this.createPageElem("pageEnd", _this.pageEnd);
	_this.createPageElem("pageNum", _this.pageNum);
	_this.createPageElem("pageSize", _this.pageSize);
	_this.createPageElem("pageCount", _this.pageCount);

	var form = $("#" + _this.bindFormId);
	form.submit();
}

hpage.prototype.createPageElem = function(name, val) {
	var _this = this;
	
	var elem = $("#{0} :input[name={1}]".format(_this.bindFormId, name));
	if(elem.length == 0) {
		$("#" + _this.bindFormId).append('<input type="hidden" name="{0}" value="{1}">'.format(name, val));
	} else {
		elem.val(val);
	}
}
