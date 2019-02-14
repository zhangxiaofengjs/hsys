function hdlg() {
	this.option = null;
};

hdlg.CACHE = {};

//hdlg.showOK("yes!!!", function(){alert("ok");});
hdlg.showOK = function(text, okFunc) {
	var dlg = new hdlg()
	dlg.showMsgDlg({
		"html": '<span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;' + text,
		"buttons": [{
			"id":"ok",
			"text":"OK",
			"click":okFunc,
			"closeOnClick": true,
		}]
	});
}

//hdlg.showYesNo("are you sure?", function(){alert("yes");}, function(){alert("no");});
hdlg.showYesNo = function(text, acceptFunc, rejectFunc) {
	var dlg = new hdlg()
	dlg.showMsgDlg({
		"html": '<span class="glyphicon glyphicon-question-sign"></span>&nbsp;' + text,
		"buttons": [{
			"id":"yes",
			"text":"是",
			"click":acceptFunc,
			"closeOnClick": true,
		},
		{
			"id":"no",
			"text":"否",
			"focus": true,
			"click":rejectFunc,
			"closeOnClick": true,
		}]
	});
}

//hdlg.showForm({
//	  "title":"abc",
//	  "fields":[
//		  {
//			  "id":"no",
//			  "type":"text",
//			  "label":"id1",
//			  "placeholder":"this is placeholder",
//			  "readonly":true,
//			  "required":true,
//			  "value":"vvvv",
//		  },
//		  {
//			  "id":"id2",
//			  "type":"number",
//			  "label":"sdsdad",
//			  "min": 1,
//			  "max": 100,
//			  "step":1,
//			  "value": 22,
//		  },
//		  {
//			  "id":"id21",
//			  "type":"number",
//			  "label":"sdsdad",
//			  "min": 1.0,
//			  "max": 100.0,
//			  "value": 22.32,
//		  },
//		  {
//			  "id":"id3",
//			  "type":"date",
//			  "label":"ssss",
//			  "value": "2018-12-12",
//		  },
//		  {
//			  "id":"id4",
//			  "type":"datetime",
//			  "label":"da",
//			  "value": "2018-12-12 10:09:21",
//		  },
//		  {
//			  "id":"id5",
//			  "type":"month",
//			  "label":"mm",
//			  "value": "2018-12",
//		  },
//		  {
//			  "id":"id6",
//			  "type":"time",
//			  "label":"sssss",
//			  "value": "10:09:21",
//		  },
//		  {
//			  "id":"id7",
//			  "type":"html",
//			  "label":"vvvvv",
//			  "value": "<table border=1 width=100%><tr><td>aaa</td><td>bbb</td></tr><tr><td>aaa</td><td>bbb</td></tr></table>",
//			  "min":1,
//		  },
//		  {
//			  "id":"id8",
//			  "type":"select",
//			  "label":"qwweqweqwe",
//			  "options":[
//				  {
//					  "text":"option a",
//					  "value":1
//				  },
//				  {
//					  "text":"option 12",
//					  "value":12
//				  },
//				  {
//					  "text":"option b",
//					  "value":3
//				  }
//			  ],
//			  "value": 12,
//			  "min":1,
//			  "max":3,
//		  },
//		  {
//			  "id":"name",
//			  "type":"text",
//			  "label":"user",
//			  "value":"bbb",
//			  "ajax":{
//				  "url":"/user/json/get",
//				  "data":{
//					  "no":"2"
//				  },
//				  "success": function(response, dlg) {
//					  console.log(response);
//					  dlg.buildField("name", response.user.name);
//					  dlg.buildField("named", response.user.no);
//				  }
//			  }
//		  },
//		  {
//			  "id":"named",
//			  "type":"text",
//			  "label":"user depend",
//			  "value":"bbb",
//			  "depend":true
//		  },
//		  {
//			  "id":"id10",
//			  "type":"select",
//			  "label":"users",
//			  "options":[],
//			  "value": "a",
//		  },
//		  {
//				"label":"id11",
//				"id":"id11",
//				"type":"selecttree",
//				"multiple":true,
//				"checkbox":true,
//				"nodeAjax":{
//				    "url":'/group/json/children', //{nodes:[{text:xxx, value:yyy}]}
//				},
//				"value": 1,
//				"dispValue": "aaaa",
//		  },
//		  {
//			  "label":"id13",
//			  "id":"id13",
//			  "type":"selecttree",
//			  "multiple":true,
//			  "checkbox":true,
//			  "value": 1,
//			  "dispValue": "aaaa",
//			  "nodes":[
//				  {
//					  "text":"第1层",
//					  "value":1,
//					  "expand":true,
//					  "nodes":[
//					  {
// 		 				"text":"第2层",
// 		 		      }]
//				  }
//			  ]
//		  },
//	  ],
//	  "ajax":{
//		  "url":"/user/json/get", 
//		  "data":{
//			  "no":"1"
//		  },
//		  "success": function(response, dlg) {
//			  
//			  //根据返回值重新构建项目
//			  var field = dlg.field("id10");
//			  field.options = [{
//				  "text":response.users[0].name,
//				  "value":response.users[0].no
//			  },
//			  {
//				  "text":response.users[1].name,
//				  "value":response.users[1].no
//			  }];
//			 // dlg.buildField("id10", "XXX");//指定初始化值
//			  dlg.buildField("id10");
//			  
//			  //测试绑定事件
//			  var elem = dlg.elem("id10");
//			  elem.bind("change", function() {
//				  var field = dlg.field("id10");
//				  var htmlElem = dlg.htmlElem("id10");
//				  var i = htmlElem.selectedIndex;
//				  console.log(i);
//				  console.log(field.options[i].value);
//			  });
//			  elem.trigger("change");//手动触发事件
//		  }
//	  },
//	  "url":"/user/update",
//	  "success":function(response) {
//		  console.log(response);
//		  alert("success");
//	  },
//	  "error":function(response) {
//		  console.log(response);
//		  alert("err");
//	  }
//});
hdlg.showForm = function(opt) {
	var dlg = new hdlg()
	dlg.showFormDlg(opt);
	
	return dlg;
}

hdlg.prototype.showMsgDlg = function(opt) {
	this.option = opt;
	var self = this;
	
	var dlgId = self.id();
	
	//gen buttons
	var strBtnHtml = "";
	if(this.option.buttons != undefined) {
		this.option.buttons.forEach(function(btn, idx) {
			strBtnHtml += '<button type="button" class="btn btn-primary btn-sm" id="{0}" name="{0}">{1}</button>'.
			format(btn.id, btn.text);
		});
	}

	//bootstrap dialog
	var strHtml = '\
		<div id="{0}" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" data-backdrop="static">\
			<div class="modal-dialog modal-sm" role="document">\
				<div class="modal-content">\
					<div class="modal-header">\
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
						<h4 class="modal-title" id="myModalLabel">提示</h4>\
					</div>\
					<div class="modal-body"><div style="word-break:break-all;">{1}</div></div>\
					<div class="modal-footer">{2}</div>\
				</div>\
			</div>\
		</div>'.format(dlgId, self.option.html, strBtnHtml);

	var targetDiv;
	var bIsCreated= false;
	if(self.option.target == undefined) {
		//gen target elem
		//获取0-100的随机数ID
		self.option.target = "hdlg_tgt_" + Math.floor(Math.random()*10000+1);
		$("body").append('<div id={0}></div>'.format(self.option.target));
		bIsCreated = true;
	}
	targetDiv = $("#"+ this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);
	
	//按钮的click回调函数
	if(self.option.buttons != undefined) {
		self.option.buttons.forEach(function(btn, idx) {
			var elem = self.elem(btn.id);
			
			if(btn.focus) {
				//对话框显示后默认focus
				elem.focus();
			}
			elem.click( function() {
				if(btn.click) {
					(btn.click)();
				}
				
				if(btn.closeOnClick) {
					self.hide();
				}
			});
		});
	}

	//销毁时移除创建tgt
	$("#" + this.id()).on('hidden.bs.modal', function(e) {
		if(bIsCreated) {
			targetDiv.remove();
		}
	});

	$("#" + dlgId).modal();
};

hdlg.prototype.id = function() {
	if(this.option == undefined ) {
		this.option = {};
	}
	if(this.option.id == undefined) {
		this.option.id = "hdlg_" + Math.floor(Math.random()*1000+1);
	}

	return this.option.id;
}

hdlg.prototype.formId = function() {
	return this.id() + "_frm";
}

hdlg.prototype.showFormDlg = function(opt) {
	var self = this;
	this.option = opt;
	
	var dlgId = self.id();
	
	var strFormHtml = '<form class="form-horizontal" id="{0}" action="{1}" enctype="{2}" method="3">'.format(
			this.formId(), 
			hsys.url(this.option.url),
			this.option.enctype || 'application/x-www-form-urlencoded',
			this.option.method || 'post');

	this.option.fields.forEach(function(f, idx) {
		if(f.type != "hidden") {
			//hidden不需要单独占行
			strFormHtml += 
					'<div class="form-group">\
						<label for="{0}" class="col-sm-3 control-label">{1}</label>\
						<div class="col-sm-9">'.
					format(f.id,
						   f.label);
		}

		if(f.buttons) {
			strFormHtml += '<div class="input-group">';
		}

		strFormHtml += self.buildFieldHtml(f, true);

		if(f.buttons) {
			f.buttons.forEach(function(btn, idx) {
				strFormHtml += '\
					<span class="input-group-btn">\
						<button name="{0}" id="{0}" class="btn btn-default" type="button">{1}</button>\
					</span></div>'.
					format(btn.id, btn.text);
			});
		}
		
		if(f.type != "hidden") {
			strFormHtml += '</div></div>';
		}
	});
	strFormHtml += '</form>';
	
	//保存Form提交用相关对象值
	hdlg.CACHE[dlgId] = {
		"dlg": self
	};
	
	var strHtml = 
		'<div id="{0}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">\
		  <div class="modal-dialog modal-md" role="document">\
		    <div class="modal-content">\
				<div class="modal-header">\
		    	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
			    	<h4 class="modal-title" id="myModalLabel">{1}</h4>\
			  	</div>\
				<div class="modal-body" id="hdlg_body">{2}\
				</div>\
				<div class="modal-footer">\
	    			<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>\
	    			<button type="button" class="btn btn-primary btn-sm" id="{0}_btn_ok" name="{0}_btn_ok" onclick="hdlg.ajaxSubmitForm(\'{0}\');">OK</button>\
				</div>\
		    </div>\
		  </div>\
		</div>'.format(dlgId, this.option.title, strFormHtml);
	
	var targetDiv;
	var bIsCreated= false;
	if(self.option.target == undefined) {
		//gen target elem
		//获取0-100的随机数ID
		self.option.target = "hdlg_tgt_" + Math.floor(Math.random()*10000+1);
		$("body").append('<div id={0}></div>'.format(self.option.target));
		bIsCreated = true;
	}
	targetDiv = $("#"+this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);

	//取得数据
	if(this.option.ajax != undefined) {//主Form Ajax
		self.doAjax();
	}

	//各种Field的ajax，以及button事件绑定
	this.option.fields.forEach(function(f, idx) {
		if(f.ajax && !f.depend) { //需要ajax并且不依赖其他ajaxDepend的项目之间初始化
			self.buildAjaxField(f);
		} else {
		}
		
		//bootstrap-select 搜索框必须刷新才能显示
		self.refreshSelectPickField(f);

		self.initTreeViewField(f);

		if(f.buttons) {
			f.buttons.forEach(function(btn, idx) {
				if(btn.hasOwnProperty("click")) {
					var elem = self.elem(btn.id);
					elem.click(function(){ (elem.click)(self); });
				}
			});
		}
	});
	
	$("#" + this.id()).on('hidden.bs.modal', function(e) {
		if(bIsCreated) {
			targetDiv.remove();
		}
		delete hdlg.CACHE[dlgId];//关闭时销毁缓存
	});
	
	$("#" + this.id()).modal();
};

hdlg.prototype.hide = function() {
	$("#" + this.id()).modal('hide');
};

hdlg.prototype.buildField = function(fieldOrId, val, bSkipAjax, bDepend ) {
	var self = this;
	
	var field = fieldOrId;
	if(typeof(fieldOrId)=="string"){
		field = this.field(field);
	}

	if(!field) {
		return;
	}
	
	if(val != undefined) {
		field.value = val;
	}
	
	if(bSkipAjax == undefined) {
		bSkipAjax = true;
	}
	
	if(bDepend != undefined) {
		field.depend = bDepend;
	}
	
	if(!bSkipAjax && field.ajax) {
		self.buildAjaxField(field);
	}
	else {
		var strFieldHtml = self.buildFieldHtml(field);

		var fieldElmParent = self.removeFieldElem(field);
		fieldElmParent.prepend(strFieldHtml);
		self.refreshSelectPickField(field);
	}
};

hdlg.prototype.field = function(id) {
	var ff = null;
	this.option.fields.some(function(f, idx) {
		if(f.id == id) {
			ff = f;
		}
	});
	
	return ff;
}

hdlg.prototype.buildFieldHtml = function(field, checkLoadElem) {
	var self = this;
	
	var strFormHtml = "";

	if(checkLoadElem) {
		if(field.ajax || field.depend) {
			//如果数据是ajax/depend（依赖后面重新做成）取得，先追加一个等待图标'
			strFormHtml += '\
				<div id="{0}" class="form-control hsys-no-boder-form-control">\
					<img src="{1}" height="24px" />\
					<span class="text-muted">加载中...</span>\
				</div>'.
				format(field.id, hsys.url("/icons/loading.gif"));
			
			return strFormHtml;
		}
	}

	if(field.type == "select") {
		strFormHtml += '<select class="form-control selectpicker show-tick" \
			data-live-search="{3}" \
			data-live-search-placeholder="{4}" \
			data-style="{5}" \
			{6} \
			data-selected-text-format="count > 99" \
			name="{0}" id="{0}" {1} {2}>'.
			format(field.id,
					field.disabled || '',
					field.multiple ? 'multiple' : '',
					field.dataLiveSearch || 'true',
					field.dataLiveSearchPlaceHolder || '输入搜索字符',
					field.dataStyle || 'btn-default',
					field.dataMaxOptions ? 'data-max-options="' + field.dataMaxOptions + '"' : ''
			);
		//$('.selectpicker').selectpicker('val', ['1','2','3']).trigger("change"); 赋值
		field.options.forEach(function(opt, idxo) {
			if(opt.group) {
				//分组
				strFormHtml += '<optgroup label="{0}">'.format(opt.text);
				
				opt.options.forEach(function(item, idxi) {
					strFormHtml += self.getSelectOptionHtml(item, field.value);
				});
				
				strFormHtml += '</optgroup>';
			}
			else
			{
				strFormHtml += self.getSelectOptionHtml(opt, field.value);
			}
		});
		strFormHtml += '</select>';
	} else if(field.type == "selecttree") {
		strFormHtml += '<div id="{0}" name={0}></div>'.
						format(field.id);
	} else if(field.type == "html") {
		strFormHtml += '<div id={0} name={0} style="padding-top:7px;">{1}</div>'.
			format(field.id, field.value || '');
	} else if(field.type == "radiogroup") {
		var strRadioGroup = "";
		field.options.forEach(function(opt, idx) {
			strRadioGroup += (idx!=0?"&nbsp;":"") + '<input type="radio" id="{0}" name="{0}" value="{1}" {2}>{3}'.
					format(field.id,
						    opt.value,
						    opt.value == field.value ?"checked":"",
						    opt.text);
		});
		strFormHtml += '<div style="padding-top:7px;">{0}</div>'.format(strRadioGroup);
	} else if(field.type == "checkboxgroup") {
		var strChBoxGroup = "";
		field.options.forEach(function(opt, idx) {
			strChBoxGroup += (idx!=0?"&nbsp;":"") + '<input type="checkbox" id="{0}" name="{0}" value="{1}" {2}>{3}'.
					format(field.id,
						    opt.value,
						    opt.value == field.value ?"checked":"",
						    opt.text);
		});
		strFormHtml += '<div style="padding-top:7px;">{0}</div>'.format(strChBoxGroup);
	} else {
		var strAttrHtml = "";
		strAttrHtml += ' type="{0}"'.format(field.type);
		strAttrHtml += ' class="{0} {1} form-control"'.format(field.type=='file'?'hsys-no-boder-form-control':'', field.class || '');
		strAttrHtml += ' name="{0}"'.format(field.id);
		strAttrHtml += ' id="{0}"'.format(field.id);
		strAttrHtml += (field.value ? ' value="{0}"'.format(field.value) : '');
		strAttrHtml += (field.placeholder ? ' placeholder="{0}"'.format(field.placeholder) : '');
		strAttrHtml += (field.readonly ? ' readonly' : '');
		strAttrHtml += (field.required ? ' required' : '');

		if($.inArray(field.type, ["number", "range", "date", "datetime", "datetime-local", "month", "time", "week"]) >=0 ) {
			strAttrHtml += (field.min ? ' min="{0}"'.format(field.min) : '');
			strAttrHtml += (field.max ? ' max="{0}"'.format(field.max) : '');
		}

		strFormHtml += '<input {0}>'.format(strAttrHtml);
	}
	
	return '<div>' + strFormHtml + '</div>';//外层的DIV是为了验证valid的时候添加input-group用
};

hdlg.prototype.getSelectOptionHtml = function(opt, value) {
	return '<option value ="{0}" {3} {2}>{1}</option>'.
			format(opt.value || '', 
					opt.text || '',
					(opt.selected || value==opt.value)?"selected":"",
					opt.dataContent ? 'data-content=\"' + opt.dataContent + "\"" : '');
}

hdlg.prototype.doAjax = function() {
	var self = this;
	var opt = this.option;
	
	$.ajax({
		url:hsys.url(opt.ajax.url),
		async:true,
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		type:"post",
		data:JSON.stringify(opt.ajax.data),
		processData: false,
        cache: false,
		success: function(response) {
			opt.ajax.success(response, self);
		},
		error: function(response) {
			var elem = self.elem("hdlg_body_msg");
			elem.remove();

			var body = self.elem("hdlg_body");
			body.append('<span class="text-danger"><span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;发生未知错误!</span>');
		}
	});
};

//ajax取得field的信息
hdlg.prototype.buildAjaxField = function(field) {
	if(field.ajax == undefined) {
		return;
	}

	var self = this;
	var ajaxOpt = field.ajax;
	
	$.ajax({
		url:hsys.url(ajaxOpt.url),
		async:true,
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		type:"post",
		data:JSON.stringify(ajaxOpt.data),
		processData: false,
        cache: false,
		context:self.elem(field.id)[0],//设置success/error的回调上下文this([0]表示转化为dom元素咯)
		success: function(response) {
			if(ajaxOpt.success) {
				(ajaxOpt.success)(response, self);
			}
		},
		error: function(response) {
			$(this).empty();
			$(this).append('<span class="text-danger"><span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;发生未知错误!</span>');
		}
	});
};

hdlg.prototype.removeFieldElem = function(field) {
	var fieldElem = this.elem(field.id);
	if(fieldElem == null || fieldElem == undefined) {
		return;
	}
	var filedElemParent = fieldElem.parent();
	
	if(field.type=='select') {
		//bootstrap-select 会产生一些其他元素，先一并删除，否则等下refresh会产生多余元素
		if(fieldElem.is("div")) {
			//如果是 加载中...的消息图标的话，直接移除即可
			fieldElem.remove();
			return filedElemParent;//返回他的亲
		}
		
		var parent = filedElemParent.parent();
		if(filedElemParent.hasClass("bootstrap-select")) {
			//是第二次加载已经被额外添加了select以外的其他元素，直接移除该亲元素即可
			filedElemParent.remove();
			return parent;//返回亲的亲
		} else {
			alert("未想定处理");
		}
	} else {
		fieldElem.remove();
		return filedElemParent;
	}
}
hdlg.prototype.refreshSelectPickField = function(fieldOrId) {
	var field = fieldOrId;
	if(typeof(field)=="string"){
		field = this.field(field);
	}
	if(field.type!='select') {
		return;
	}
	
	this.elem(field.id).selectpicker('refresh');
};

hdlg.prototype.initTreeViewField = function(fieldOrId) {
	var self = this;
	
	var field = fieldOrId;
	if(typeof(field)=="string"){
		field = this.field(field);
	}
	if(field.type!='selecttree') {
		return;
	}

	htreeview.initPullDown({
		"id": field.id,
		"multiple":field.multiple,
		"checkbox":field.checkbox,
		"nodes":field.nodes,
		"ajax":field.nodeAjax,
	});

	htreeview.setPullDownValue(field.id, field.value, field.dispValue);
}

hdlg.prototype.elemId = function(id, prefix) {
	//从当前dlg往下查找子元素，否则容易和画面其他元素发生重名，导致错误
	var strId = "#{0} [id='{1}{2}']".format(
		this.id(),
		prefix != undefined ? prefix : '',
		id.safeJqueryId());
	
	return strId;
};

//取得JsQuery元素
hdlg.prototype.elem = function(id, prefix) {
	var strId = this.elemId(id, prefix);
	return $(strId);
};

//取得html元素
hdlg.prototype.htmlElem = function(id) {
	var strId = this.elemId(id);
	return $(strId)[0];
};

hdlg.prototype.doFieldValid = function(field) {
	var self = this;
	var fieldElm = self.elem(field.id);
	var val = fieldElm.val();
	
	if(field.required != undefined && !val) {
		self.setError(field, "这个值不能为空");
		return false;
	}
	if(field.min != undefined && (val == "" || val == null || val.compareNumber(field.min) < 0)) {
		self.setError(field, "这个值必须大于等于" + field.min);
		return false;
	}
	if(field.max != undefined && (val == ""|| val == null || val.compareNumber(field.max) > 0)) {
		self.setError(field, "这个值必须小于等于" + field.max);
		return false;
	}

	if(field.hasOwnProperty("valid")) {
		var errMsg = (field.valid)();
		if(errMsg != '') {
			self.setError(field, errMsg);
			return false;
		}
	}
	
	self.setError(field, "");
	return true;
} 
	
hdlg.prototype.setError = function(fieldOrId, msg) {
	var self = this;

	var field = fieldOrId;
	if(typeof fieldOrId == "string") {
		field = self.field(fieldOrId);
	}

	var fieldElem = this.elem(field.id);
	if(fieldElem == null || fieldElem == undefined) {
		return;
	}
	var filedElemParent = fieldElem.parent();
	
	if(field.type=='select') {
		if(filedElemParent.hasClass("bootstrap-select")) {
			//是第二次加载已经被额外添加了select以外的其他元素
			filedElemParent = filedElemParent.parent();
		} else {
			return;
		}
	}

	var span = filedElemParent.children("[id='err']");
	if(msg != "" && span.length == 0) {
		filedElemParent.append('<span id="err" class="input-group-addon" style="border:0px;background-color:rgba(0,0,0,0);"><span class="text-danger"><span class="glyphicon glyphicon-exclamation-sign"></span></span></span>');
		span = filedElemParent.children("[id='err']");
	}
	if(msg == "") {
		span.hide();
		filedElemParent.removeClass("input-group");
	} else {
		span.attr("title", msg);
		filedElemParent.addClass("input-group");
		span.show();
	}
}

hdlg.prototype.doFormValid = function() {
	var self = this;
	var isOK = true;
	
	self.option.fields.forEach(function(field, idx) {
		if(!self.doFieldValid(field)) {
			isOK = false;
		}
	});
	
	if(self.option.hasOwnProperty("valid")) {
		if(!(self.option.valid)()) {
			isOK = false;
		}
	}
	return isOK;
};

hdlg.prototype.afterSubmit = function() {
	var self = this;
	
	if(self.option.hasOwnProperty("afterSubmit")) {
		isOK = (self.option.afterSubmit)();
	}
};

//formJson = {
//	'num': 11,
//	'bom':{
//		'id':22
//	}
//};
//将 a.b.c=1的名字转化为{'a':{'b':{'c':1}}}返回b对象,
//将 a.b[0].c=1的名字转化为{'a':{'b':[{'c':1}]}}返回b对象,
//将 a.b.c[0]=1的名字转化为{'a':{'b':{'c':[1]}}}返回b对象,
function getJsonObj(o, strName) {
	var names = strName.split(".");
	
	var ret = o;
	for(var i = 0; i < names.length - 1; i++) {
		var name = names[i];
		
		var isArray = false;
		var arrIndex = 0;
		if(name.substring(name.length-1, name.length) == "]") {
			//如果是数组
			isArray = true;
			var is = name.indexOf("[");
			var ie = name.indexOf("]");
			arrIndex = parseInt(name.substring(is+1, ie));
			name = name.substring(0, is);
		}
			
		if (ret[name] == undefined) {
			if(isArray) {
				ret[name] = [];
				
				var idx = arrIndex;
				while(idx>-1) {
					ret[name].push({});//放0到指定位置空对象
					idx--;
				}
			} else {
				ret[name] = {};
			}
		}
		
		//取元素
		ret = ret[name];

		//如果是数组的话，取得数组位置的元素
		if(isArray) {
			while(ret.length <= arrIndex) {
				ret.push({});//放0到指定位置空对象
			}
			
			ret = ret[arrIndex];
		}
	}
	
	return ret;
}

hdlg.encodeFormJson = function(frm) {
   var o = {};
   var a = $(frm).serializeArray();

   $.each(a, function() {
		var obj = getJsonObj(o, this.name);
		var names = this.name.split(".");
		var propName = names.pop();
		   
		//准备设定属性值
		if(propName.substring(propName.length-1, propName.length) == "]") {
			//如果是数组,值类型改为数组
			var is = propName.indexOf("[");
			var ie = propName.indexOf("]");
			var arrIndex = parseInt(propName.substring(is+1, ie));
			propName = propName.substring(0, is);

			var valArry = obj[propName];
			if(valArry == undefined) {
				valArry = [];
				obj[propName] = valArry;
			}
			while(valArry.length <= arrIndex) {
				valArry.push(undefined);//放0到指定位置空对象
			}
			valArry[arrIndex] = this.value || '';
		} else {
			//a.b.c和a.b.c两个的时候，转换为{'a':{'b':{'c':[1,2]}}}
			if(obj.hasOwnProperty(propName)) {
				//已经存在的key，变成数组
				var val = obj[propName];
				if($.isArray(val)) {
					val.push(this.value || '');
				} else {
					var array = new Array();
					array.push(val);
					array.push(this.value || '');
					obj[propName] = array;
				}
			} else {
				obj[propName] = this.value || '';
			}
		}
   });
   return o;
};

//静态方法
hdlg.ajaxSubmitForm = function(dlgId) {
	var args = hdlg.CACHE[dlgId];
	if(args == null) {
		alert("sys:The dlg is not found!!");
		return;
	}
	
	var dlg = args.dlg;
	if(!dlg.doFormValid()) {
		return;
	}
	var option = dlg.option;
	var formData;
	var contentType;
	if(option.enctype == "multipart/form-data") {
		formData = new FormData($("#"+dlg.formId().safeJqueryId())[0]);
		contentType = false;
	} else {
		var formJson = hdlg.encodeFormJson("#"+dlg.formId());
		//	var ff = new FormData();//FormData不好用，暂用json代替
		formData = JSON.stringify(formJson);
		contentType = "application/json;charset=UTF-8";
	}
	var args = {
    	url : hsys.url(option.url),
        type : 'post',
        dataType : 'json',//接受服务端数据类型
        contentType:contentType,
        processData: false,
        cache: false,
        data: formData,
        success : function(data) {
        	if(data.success) {
        		if(typeof option.closeOnSuccess == "undefined" || option.closeOnSuccess) {
        			dlg.hide();
        		}
        		option.success(data, dlg);
        	}
        	else {
        		option.error(data, dlg);
        	}
        },
        error: function(data) {
        	hsys.sysError();
        }
     };
    
	//提交
	$.ajax(args);

	dlg.afterSubmit();
}
