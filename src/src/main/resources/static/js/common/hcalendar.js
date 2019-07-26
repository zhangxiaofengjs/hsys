function hcalendar() {
	this._selectedDate = new Date();
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
	_this.bindEvent();
	_this.showCalendarData();
}
hcalendar.prototype.addItem = function(date,html) {
	var _this = this;
	var tds = $("#"+ _this.target.safeJqueryId()+" td[data='{0}']".format(hdate.yyyy_MM_dd(new Date(date))));
	tds.append(html);
}
hcalendar.prototype.addAll = function(html) {
	var _this = this;
	var tds = $("#"+ _this.target.safeJqueryId()+" td");
	tds.append(html);
}

/**
 * 渲染html结构
 */
hcalendar.prototype.buildCalendarTableHtml = function() {
	// 设置标题盒子中的html
    var titleBox = '<div class="calendar-title-box">\
    					<span class="prev-month" id="prevMonth"></span>\
					    <span class="calendar-title" id="calendarTitle"></span>\
					    <span id="nextMonth" class="next-month"></span>\
    				</div>';
    
 // 设置表格区的html结构
    var _headHtml = '\
		<tr>\
			<th>日</th>\
			<th>一</th>\
			<th>二</th>\
			<th>三</th>\
			<th>四</th>\
			<th>五</th>\
			<th>六</th>\
		</tr>';
    var _bodyHtml = "";
    // 行表格
    for(var i = 0; i < 6; i++) {
    	_bodyHtml += '\
		<tr> \
			<td></td>\
			<td></td>\
			<td></td>\
			<td></td>\
			<td></td>\
			<td></td>\
			<td></td>\
		</tr>';
    }
    var bodyBox = '<div class="calendar-body-box">\
    					<table id="calendarTable" class="calendar-table">'+
    						_headHtml + _bodyHtml +
    					'</table>\
    				</div>';
    var calendar = titleBox + bodyBox;
	return calendar;
}

hcalendar.prototype.showCalendarData = function() {
	var _this = this;
	
	var date = _this._selectedDate;
	var _year = date.getFullYear();
    var _month = date.getMonth() + 1;
 
    // 设置顶部标题栏中的 年、月信息
    var titleStr = hdate.format(date, "yyyy年MM月");
    $("#calendarTitle").text(titleStr);
 
    // 设置表格中的日期数据
    var tds = $("#"+ _this.target.safeJqueryId()+" td");
    var firstDay = new Date(_year, _month - 1, 1);  // 当前月第一天
    for(var i = 0; i < tds.length; i++) {
    	var pTd = $(tds[i]);
    	
    	var thisDay = new Date(_year, _month - 1, i + 1 - firstDay.getDay());
    	
    	pTd.text(thisDay.getDate());
    	
    	pTd.append("<br/>");
    	
    	pTd.attr('data', hdate.yyyy_MM_dd(thisDay));
    	pTd.removeClass("currentDay");
    	pTd.removeClass("currentMonth");
    	pTd.removeClass("otherMonth");
		if(hdate.yyyyMMdd(thisDay) == hdate.yyyyMMdd(new Date())) {// 当前天
			pTd.addClass("currentDay");
		} else if(hdate.yyyyMM(thisDay) == hdate.yyyyMM(new Date())) {
			pTd.addClass("currentMonth");  // 当前月
		} else {    // 其他月
			pTd.addClass("otherMonth");
		}
    }
    
    if(_this.hasOwnProperty('dateChanged')) {
    	tds.children().remove();
    	(_this.dateChanged)();
    	
    	
    }
}

/**
 * 绑定上个月下个月事件
 */
hcalendar.prototype.bindEvent = function() {
	var _this = this;
	$("#prevMonth").click(function(){
		_this._selectedDate = hdate.addMonth(_this._selectedDate, -1);
		_this.showCalendarData();
	});
	$("#nextMonth").click(function(){
		_this._selectedDate = hdate.addMonth(_this._selectedDate, 1);
		_this.showCalendarData();
	});
}
