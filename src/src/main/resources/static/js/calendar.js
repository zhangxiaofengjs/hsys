(function(){
  /*
   * 用于记录日期，显示的时候，根据dateObj中的日期的年月显示
   */
  var dateObj = (function(){
    var _date = new Date();    // 默认为当前系统时间
    return {
      getDate : function(){
        return _date;
      },
      setDate : function(date) {
        _date = date;
      }
    };
  })();
 
  // 设置calendar div中的html部分
  renderHtml();
  // 表格中显示日期
  showCalendarData();
  // 绑定事件
  bindEvent();
  add();
 
  /**
   * 渲染html结构
   */
  function renderHtml() {
    var calendar = document.getElementById("calendar");
    var titleBox = document.createElement("div");  // 标题盒子 设置上一月 下一月 标题
    var bodyBox = document.createElement("div");  // 表格区 显示数据
 
    // 设置标题盒子中的html
    titleBox.className = 'calendar-title-box';
    titleBox.innerHTML = "<span class='prev-month' id='prevMonth'></span>" +
      "<span class='calendar-title' id='calendarTitle'></span>" +
      "<span id='nextMonth' class='next-month'></span>";
    calendar.appendChild(titleBox);    // 添加到calendar div中
    
    // 设置表格区的html结构
    bodyBox.className = 'calendar-body-box';
    
    var _headHtml = "<tr>" + 
              "<th>日</th>" +
              "<th>一</th>" +
              "<th>二</th>" +
              "<th>三</th>" +
              "<th>四</th>" +
              "<th>五</th>" +
              "<th>六</th>" +
            "</tr>";
    var _bodyHtml = "";
 
    // 行表格
    for(var i = 0; i < 6; i++) {  
      _bodyHtml += "<tr>" +
              "<td></td>" +
              "<td></td>" +
              "<td></td>" +
              "<td></td>" +
              "<td></td>" +
              "<td></td>" +
              "<td></td>" +
            "</tr>";
    }
    
    bodyBox.innerHTML = "<table id='calendarTable' class='calendar-table'>" +
	_headHtml + _bodyHtml+
	"</table>";
    // 添加到calendar div中
    calendar.appendChild(bodyBox);
  }
  
  /**
   * 表格中显示数据，并设置类名
   */
  function showCalendarData() {
    var _year = dateObj.getDate().getFullYear();
    var _month = dateObj.getDate().getMonth() + 1;
    var _dateStr = getDateStr(dateObj.getDate());
 
    // 设置顶部标题栏中的 年、月信息
    var calendarTitle = document.getElementById("calendarTitle");
    var titleStr = _dateStr.substr(0, 4) + "年" + _dateStr.substr(5,2) + "月";
    calendarTitle.innerText = titleStr;
 
    // 设置表格中的日期数据
    var _table = document.getElementById("calendarTable");
    var _tds = _table.getElementsByTagName("td");
    var _firstDay = new Date(_year, _month - 1, 1);  // 当前月第一天
    var btn ="";
    btn = "<div>"+"<input id='extra' name='加班' type='image' src='{0}' />".format(hsys.url('/icons/extra.png'))+"<input id='rest' name='请假' type='image' src='{0}' />".format(hsys.url('/icons/rest.png'))+"</div>";
    for(var i = 0; i < _tds.length; i++) {
      var _thisDay = new Date(_year, _month - 1, i + 1 - _firstDay.getDay());
      var _thisDayStr = getDateStr(_thisDay);
      _tds[i].innerHTML = _thisDay.getDate()+btn;
      //_tds[i].data = _thisDayStr;
      _tds[i].setAttribute('data', _thisDayStr);
      if(_thisDayStr == getDateStr(new Date())) {    // 当前天
        _tds[i].className = 'currentDay';
      }else if(_thisDayStr.substr(0, 7) == getDateStr(_firstDay).substr(0, 7)) {
        _tds[i].className = 'currentMonth';  // 当前月
      }else {    // 其他月
        _tds[i].className = 'otherMonth';
      }
    }
    
    var holiday=getHolidayData();
    for(var i = 0; i < _tds.length; i++) {
        var _thisDay = new Date(_year, _month - 1, i + 1 - _firstDay.getDay());
        var _thisDayStr = getDateStr(_thisDay);
    var add = "";
    //alert(holiday.holiday[0].date);
    for(var j = 0; j < holiday.date.length; j++) {
    	if(_thisDayStr == holiday.date[j]) {    // holiday 
    		switch(holiday.type[j])
    		{
    		case 0:
    			add += "<img alt='rest' src='{0}'>".format(hsys.url('/icons/holiday.png'))+"<li title='"+holiday.comment[j]+"'>"+' &nbsp' + holiday.comment[j] + "</li>";
    			break;
    		case 1:
    			add += "<img alt='change' src='{0}'>".format(hsys.url('/icons/change.png'))+"<li title='"+holiday.comment[j]+"'>"+' &nbsp' + holiday.comment[j] + "</li>";
    			break;
    		case 2:
    			add += "<img alt='work' src='{0}'>".format(hsys.url('/icons/work.png'))+"<li title='"+holiday.comment[j]+"'>"+' &nbsp' + holiday.comment[j] + "</li>";
    			break;
    		}
    		_tds[i].innerHTML = _thisDay.getDate()+btn+"<br/>"+add;
    		if(getDateStr(new Date()) == holiday.date[j]) {
    			_tds[i].className = 'currentDay'; 
    		}
    		else{_tds[i].className = 'holiday';}
    		// alert(holiday.holiday[0].date);
    	}
    }
    }
  }
 
  /**
   * 绑定上个月下个月事件
   */
  function bindEvent() {
    var prevMonth = document.getElementById("prevMonth");
    var nextMonth = document.getElementById("nextMonth");
    addEvent(prevMonth, 'click', toPrevMonth);
    addEvent(nextMonth, 'click', toNextMonth);
    var table = document.getElementById("calendarTable");
    var tds = table.getElementsByTagName('td');
    	for(var i = 0; i < tds.length; i++) {
    		addEvent(tds[i], 'click', function(e){
	    	console.log(e.target.getAttribute('data'));
	    });
	  }
  }
 
  /**
   * 绑定事件
   */
  function addEvent(dom, eType, func) {
    if(dom.addEventListener) {  // DOM 2.0
      dom.addEventListener(eType, function(e){
        func(e);
      });
    } else if(dom.attachEvent){  // IE5+
      dom.attachEvent('on' + eType, function(e){
        func(e);
      });
    } else {  // DOM 0
      dom['on' + eType] = function(e) {
        func(e);
      }
    }
  }
 
  /**
   * 点击上个月图标触发
   */
  function toPrevMonth() {
    var date = dateObj.getDate();
    dateObj.setDate(new Date(date.getFullYear(), date.getMonth() - 1, 1));
    showCalendarData();
    add();
  }
 
  /**
   * 点击下个月图标触发
   */
  function toNextMonth() {
    var date = dateObj.getDate();
    dateObj.setDate(new Date(date.getFullYear(), date.getMonth() + 1, 1));
    showCalendarData();
    add();
  }
 
  /**
   * 日期转化为字符串， 4位年+2位月+2位日
   */
  function getDateStr(date) {
    var _year = date.getFullYear();
    var _month = date.getMonth() + 1;    // 月从0开始计数
    var _d = date.getDate();
     
    _month = (_month > 9) ? ("-" + "" + _month) : ("-" + "0" + _month);
    _d = (_d > 9) ? ("-" + "" + _d) : ("-" + "0" + _d);
    return _year + _month + _d;
  }
  
  /**
   * 取DB数据
   */
  function getHolidayData(){
	  var list = {
			  holiday: [
	              {
	                  date:[],
	                  type:[],
	                  comment:[]
	              }
	          ]
	      };
  $.ajax({
	async: false,
  	type:"post",
  	url:"/hsys/user/json/get",
  	cache:false,
  	dataType: "json",
  	success:function(res){
  		for(var i = 0; i < res.extratime.length ; i++) {
  		var  extratime=res.extratime;
  		list.holiday[0].date.push(extratime[i].date);
  		list.holiday[0].type.push(extratime[i].type);
  		list.holiday[0].comment.push(extratime[i].comment);
  		 }
  	},
  		 error: function (errorMsg) {
               alert("request data failed!!!");
           }
  });
  return list;
  }
  
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
	
	function addExtra(){
		var self = $(this);
		var user = $(":hidden[name='user']").val();
		hdlg.showForm({
			"title":"加班登记",
			"fields":[
				{
					"id":"date",
					"label":"加班日期",
					"type":"date",
					"required":true,
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
					"type":"radiogroup",
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
					"type": user ? "hidden" : "select",//用户画面无需显示用户
					"options": [],
					"ajax": (user ? undefined : {
						"url":"/user/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("userId");
							response.users.forEach(function(user,index){
								field.options.push(
										{ 
											"text":"["+ user.no +"]" + user.name,
											"value":user.id,
										}
								);
							});
							dlg.buildField("userId");
						},
					}),
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
	function addRest(){
		var self = $(this);
		
		var user = $(":hidden[name='user']").val();

		hdlg.showForm({
			"title":"请假登记",
			"fields":[
				{
					"id":"userId",
					"label":"申请人",
					"type": user ? "hidden" : "select",//用户画面无需显示用户
					"options": [],
					"ajax": (user ? undefined : {
						"url":"/user/json/list",
						 "success": function(response, dlg) {
							 var field = dlg.field("userId");
							 response.users.forEach(function(user,index){
								 field.options.push(
									 { 
									    "text":"["+user.no+"] "+user.name,
										"value":user.id,
									 }
								 );
							 });
							  dlg.buildField("userId");
						 },
					}),
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
	};
	
/**
   * 绑定添加按钮
   */
	var tableDate="";
	function add(){
	 var add=document.getElementsByTagName('input');
	  for(var i=0;i< add.length;i++){
		  add[i].onclick=function () {
			  if($(this).attr("id")=="extra"){
				  tableDate=$(this).parent().parent().attr("data");
				  addExtra();
			  }
			  else{
				  tableDate=$(this).parent().parent().attr("data")+"T";
				  addRest();
			  }
		}
	  }
	}
})();


