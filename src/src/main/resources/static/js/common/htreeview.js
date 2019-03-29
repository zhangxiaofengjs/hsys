function htreeview() {
	this._gid = 0;
	this._id = Math.floor(Math.random()*10000+1);
	this.nodeIdMap = new Object();

	htreeview.CACHE[this._id] = this;
};

htreeview.CACHE = {};

htreeview.init = function(opt) {
	var tv = new htreeview()
	tv.build(opt);
	
	return tv;
};

htreeview.initPullDown = function(opt) {
	var strHtml = '<div id="seltree_{0}">\
		<button type="button" id="seltreebtn_{0}" class="form-control btn btn-default" style="width:100%;text-align:left">\
			<span id="seltreedisp_{0}"></span>\
			<span class="caret pull-right" style="margin-top:8px;"></span>\
		</button>\
		<div id="seltreetgt_{0}"></div></div>'.format(opt.id);

	var elem = $("#" + opt.id.safeJqueryId());
	var val = elem.val();
	var dispText = elem.attr("dispText");

	//替换原来的元素
	elem.after(strHtml);
	elem.remove();

	//设置初始值
	if(val != undefined && dispText != undefined) {
		htreeview.setPullDownValue(opt.id, val, dispText);
	}

	opt.target = "seltreetgt_" + opt.id;
	var tv = new htreeview()
	tv.build(opt);

	if(opt.hasOwnProperty("css")) {
		var tgtDivElem = $("#seltree_" + opt.id.safeJqueryId());
		tgtDivElem.css(opt.css);
	}

	//以下下拉的动作
	var tgtElem = $("#" + tv.target.safeJqueryId());
	//tgtElem.addClass("pre-scrollable");
	tgtElem.addClass("htv-display-none");//先隐藏，点击下拉时再显示

	var parentElem = $("#seltreebtn_" + opt.id.safeJqueryId());

	//点击显示下拉树
	$(("#seltreebtn_" + opt.id).safeJqueryId()).click(function(){
		//listgroup追加float以便产生滚动条后子元素可以自由扩张大小
		var listGrpElem = tgtElem.children("div:first-child");
		listGrpElem.css({
			"float":"left",
			"margin-bottom":"0px",
			"min-width": (parentElem.outerWidth() - 19) + "px",//减去滚动条
		});
		
		//下拉时取出按钮下圆角
		parentElem.addClass("htv-no-bottom-border-radius");
		
		//算定位，参考css2.0 absolute计算方法
		tgtElem.css({
			"z-index": 999,
			"position": "absolute",
			"max-height": "300px",
			"overflow-y": "scroll",//target追加可以滚动，以便内容太长实现滚动条
			"backgound-color": "#FFFFFF",
			"border": "1px solid #ddd",
			"top": (parentElem.position().top + parentElem.outerHeight()) + "px",
			"min-width": parentElem.outerWidth() + "px",
			"width": parentElem.outerWidth() + "px",
		}).slideDown("fast");

		var onBodyDown = function() {
			if (event.target.id != "seltreebtn_" + opt.id &&
				event.target.id != tv.target &&
				$(event.target).parents("#"+ tv.target.safeJqueryId()).length==0) {
				tgtElem.fadeOut("fast");
				
				parentElem.removeClass("htv-no-bottom-border-radius");
				
				$("body").unbind("mousedown", onBodyDown);
			}
		}
		
		//点击后事件
		$("body").bind("mousedown", onBodyDown);
	});

	//nodeClick事件
	tv.nodeClick = function(node, tv) {
		var self = this;
		
		//值设定
		if(!tv.multiple) {
			htreeview.setPullDownValue(opt.id, node.val(), node.text);
			tv.hide();
		}
	}
	
	tv.checkChanged = function(node, tv) {
		var self = this;
		
		//值设定
		if(!tv.multiple) {
			return;
		}

		var nodes = tv.checkedNodes();
		var dispArr = new Array();
		var valArr = new Array();
		nodes.forEach(function(n, idx) {
			if(n.isLeaf()) {
				dispArr.push(n.text);
				valArr.push(n.val());
			}
		});
		htreeview.setPullDownValue(opt.id, valArr, dispArr);
	}
	return tv;
};

htreeview.setPullDownValue = function(id, val, text) {
	$("#seltreedisp_" + id).html(text);
	
	var parentElem = $("#seltree_" + id.safeJqueryId());
	//删除所有hidden，然后追加赋值
	$("#seltree_{0} input:hidden[name='{0}']".format(id.safeJqueryId())).remove();
	
	var valArr = $.isArray(val) ? val : [val]; 
	var textArr = $.isArray(text) ? text : [text]; 
	valArr.forEach(function(v, idx) {
		strHtml = '<input type="hidden" name="{0}" value="{1}">'.
			format(id, v);
		parentElem.append(strHtml);
	});
	
	$("#seltreedisp_" + id.safeJqueryId()).html(textArr.join(","));
}

htreeview.prototype.build = function(opt) {
	var _this = this;
	
	//opt上的属性全部复制到自己上面
	$.extend(this, opt);
	
	var strHtml = '<div class="list-group" id="tv_{0}">'.format(this._id);
	strHtml += '</div>';

	//追加亲元素
	var tgtElem = $("#"+ _this.target.safeJqueryId());
	tgtElem.children().remove();
	tgtElem.append(strHtml);

	//追加子节点
	if(_this.hasOwnProperty("ajax")) {
		_this.buildAjaxNode(null);
	} else if (_this.hasOwnProperty("nodes") && $.isArray(_this.nodes)) {
		strHtml = "";
		_this.nodes.forEach(function(node, idx) {
			strHtml += _this.buildNodeHtml(node, null);
		});

		var listGrpElem = tgtElem.children("div:first-child");
		listGrpElem.append(strHtml);

		//节点的图标点击事件
		if($.isArray(_this.nodes)) {
			_this.bindNodeEvent(_this.nodes);
		}
	}
};

htreeview.prototype.buildNodeHtml = function(node, parentNode) {
	var _this = this;
	
	_this._gid++;
	
	//内部用IDと方法の拡張
	if(parentNode == null || !parentNode.hasOwnProperty("_level")) {
		node._level = 0;
	} else {
		node._level = parentNode._level + 1;
	}
	node._id = this.nodeIdPrefix() + (this._gid);
	node._parent = parentNode;
	node["hasChildren"] = function() {
		return (this.hasOwnProperty("nodes") && this.nodes.length != 0);
	};
	node["isLeaf"] = function() {
		return !this.hasChildren();
	};
	node["isAncestorExpand"] = function() {
		if(this._parent == null || this._parent == undefined) {
			return true;
		}
		if(!this._parent.expand) {
			return false;
		}
		return this._parent.isAncestorExpand();
	};
	node["val"] = function() {
		return this.hasOwnProperty("value")? this.value : this.text;
	};
	node["_isAjaxLoad"] = function() {
		return (_this.hasOwnProperty("ajax") || this.hasOwnProperty("ajax") ) && 
			this.hasOwnProperty("_ajaxLoad") && this._ajaxLoad;
	};
	
	this.nodeIdMap[node._id] = node;
	
	var handlecls ="";
	var cls ="";
	var margin = node._level*16;
	if(node.hasChildren()) {
		handlecls = "glyphicon";
		if(node.expand) {
			handlecls += " glyphicon-triangle-bottom"; 
		} else {
			handlecls += " glyphicon-triangle-right"; 
		}
	}
	else if((_this.hasOwnProperty("ajax") || this.hasOwnProperty("ajax") ) &&
			!node._isAjaxLoad()) {
		handlecls = "glyphicon glyphicon-triangle-right";
	} else {
		margin += 16;
	}
	if(!node.isAncestorExpand()) {
		cls += " htv-display-none";
	}

	var strHtml = "";
	strHtml += '<a class="{4} htv-no-border-radius list-group-item" id="{3}"> \
					<div style="margin-left:{1}px;" id="{7}">\
						<div style="display:inline;width:14px;"><span class="{0}" id="{5}"></span></div>\
						<div style="display:inline;overflow-wrap:nowrap;white-space:nowrap;">{6}{2}</div>\
					</div>\
				</a>'.format(
				handlecls,
				margin,
				node.text,
				node._id,
				cls,
				_this.nodeHandleIdPrefix() + (_this._gid),
				_this.hasCheckBox(node) ?'<input type="checkbox" id="{0}">'.format(_this.nodeCheckBoxIdPrefix() + _this._gid):'',
				_this.nodeContentIdPrefix() + (_this._gid));
	
	if(node.hasChildren()) {
		node.nodes.forEach(function(n, idx) {
			strHtml += _this.buildNodeHtml(n, node);
		});
	}
	
	return strHtml;
}

htreeview.prototype.buildAjaxNode = function(node) {
	var _this = this;

	var url;
	var param;
	if(node != null && node.hasOwnProperty("ajax")) {
		url = node.ajax.url;
		param = node.ajax.data;
	} else {
		url = _this.ajax.url;
		param = _this.ajax.data;
	}
	if(param == undefined) {
		param = {};
	}
	var pElem;
	if(node != null) {
		param.value = node.val();
		pElem = _this.nodeElem(node);
	} else {
		pElem = $(("#tv_" + _this._id).safeJqueryId());
	}

	$.ajax({
		url:hsys.url(url),
		async:true,
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		type:"post",
		data:JSON.stringify(param),
		processData: false,
        cache: false,
		context:pElem[0],//设置success/error的回调上下文this([0]表示转化为dom元素咯)
		success: function(response) {
			var nodes = response.nodes;
			if($.isArray(nodes)) {
				var strHtml = "";
				nodes.forEach(function(n, idx) {
					strHtml += _this.buildNodeHtml(n, node);
				});
				if(node == null) {
					//根节点
					_this._ajaxLoad = true;
					_this.nodes = nodes;
					$(this).append(strHtml);
				} else {
					node._ajaxLoad = true;
					node.expand = true;
					node.nodes = nodes;
					$(this).after(strHtml);
					_this._toogleNodeVisable(node);
				
					if(nodes.length == 0) {
						//如果没有子的话，应当去掉handle图标
						var nodeHandleElem = _this.nodeHandleElem(node);
						nodeHandleElem.removeClass("glyphicon glyphicon-triangle-bottom glyphicon-triangle-right");
						//亲容器往后缩进一下
						var cElem = _this.nodeContentElem(node);
						cElem.css("margin-left:{0}px".format(node._level*16+16));
					}
				}
				//事件绑定
				_this.bindNodeEvent(nodes);
			}
		},
		error: function(response) {
			var nodes = [{
				"text":'<span class="text-danger"><span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;发生未知错误!</span>',
			}];
			var strHtml = _this.buildNodeHtml(nodes[0], node);
			if(node == null) {
				$(this).append(strHtml);
			} else {
				node._ajaxLoad = true;
				node.expand = true;
				node.nodes = nodes;
				$(this).after(strHtml);
				_this._toogleNodeVisable(node);
			}
		}
	});
}

htreeview.prototype.hasCheckBox = function(node) {
	var _this = this;
	
	var isCheckBox = false;
	if(node.hasOwnProperty("checkbox") &&
		node.checkbox) {
		isCheckBox = true;
	}
	if(_this.hasOwnProperty("checkbox") &&
		_this.checkbox) {
		if(!node.hasOwnProperty("checkbox")) {
			isCheckBox = true;
		} else if(node.checkbox){
			isCheckBox = true;
		}
	}
	
	return isCheckBox;
}

htreeview.prototype.nodeIdPrefix = function() {
	return "htv" + this._id + "n";
}

htreeview.prototype.nodeHandleIdPrefix = function() {
	return "nh_" + this.nodeIdPrefix();
}

htreeview.prototype.nodeCheckBoxIdPrefix = function() {
	return "ncb_" + this.nodeIdPrefix();
}

htreeview.prototype.nodeContentIdPrefix = function() {
	return "ncnt_" + this.nodeIdPrefix();
}

htreeview.prototype.nodeFromNodeHandleId = function(nhid) {
	return this.nodeIdMap[nhid.substring(3)];
}

htreeview.prototype.nodeFromNodeCbId = function(nhid) {
	return this.nodeIdMap[nhid.substring(4)];
}

htreeview.prototype.nodeFromNodeId = function(nhid) {
	return this.nodeIdMap[nhid];
}

htreeview.prototype.nodeElem = function(node) {
	return $('#' + node._id);
}

htreeview.prototype.nodeHandleElem = function(node) {
	var elem = $('#nh_' + node._id);
	if(elem.length == 0) {
		return null;
	}
	return elem;
}

htreeview.prototype.nodeCheckBoxElem = function(node) {
	var elem = $('#ncb_' + node._id);
	if(elem.length == 0) {
		return null;
	}
	return elem;
}

htreeview.prototype.nodeContentElem = function(node) {
	var elem = $('#ncnt_' + node._id);
	if(elem.length == 0) {
		return null;
	}
	return elem;
}

htreeview.prototype.bindNodeEvent = function(nodes) {
	var _this = this;
	
	nodes.forEach(function(n, idx) {
		//展开图标事件
		var nodeHandleElem = _this.nodeHandleElem(n);
		if(nodeHandleElem != null) {
			nodeHandleElem.click(function(){
				_this._onNodeHandleClick(nodeHandleElem);
			});
		}
		
		//节点的CheckBox点击事件
		var nodeCheckBoxElem = _this.nodeCheckBoxElem(n);
		if(nodeCheckBoxElem != null) {
			nodeCheckBoxElem.click(function(){
				_this._onNodeCheckBoxClick(nodeCheckBoxElem);
			});
		}
		
		////节点的点击事件
		var nodeElem = _this.nodeElem(n);
		if(nodeElem != null) {
			nodeElem.click(function(){
				_this._onNodeClick(nodeElem);
			});
		}
		
		if(n.hasChildren()) {
			_this.bindNodeEvent(n.nodes);	
		}
	});
}

htreeview.prototype._onNodeHandleClick = function(nodeHandleElem) {
	var _this = this;
	var node = _this.nodeFromNodeHandleId(nodeHandleElem.prop("id"));
	
	if(_this.hasOwnProperty("ajax") || node.hasOwnProperty("ajax")) {
		if(!node._isAjaxLoad()) {
			_this.buildAjaxNode(node);
			return;
		}
	}
	
	if(!node.hasChildren()) {
		return;
	}

	node.expand = !node.expand;

	_this._toogleNodeVisable(node);
}

htreeview.prototype._toogleNodeVisable = function(node) {
	var _this = this;
	
	var nodeHandleElem = _this.nodeHandleElem(node);
	if(nodeHandleElem == null) {
		return;
	}
	if(node.hasChildren()) {
		if(node.expand) {
			nodeHandleElem.addClass('glyphicon-triangle-bottom');
			nodeHandleElem.removeClass('glyphicon-triangle-right');
		} else {
			nodeHandleElem.removeClass('glyphicon-triangle-bottom');
			nodeHandleElem.addClass('glyphicon-triangle-right');
		}
	}
	
	//当前node的可视化，还取决于亲是否展开
	if(node.isAncestorExpand()) {
		_this.nodeElem(node).removeClass('htv-display-none');
	} else {
		_this.nodeElem(node).addClass('htv-display-none');
	}

	//因为亲的展开合并，子也需要做可视化处理
	if(node.hasChildren()){ 
		node.nodes.forEach(function(n, idx) {
			_this._toogleNodeVisable(n);
		});
	}
}

htreeview.prototype._onNodeCheckBoxClick = function(nodeCbElem) {
	var _this = this;
	var node = _this.nodeFromNodeCbId(nodeCbElem.prop("id"));

	//如果子check住了，check所有的亲
	if(nodeCbElem.prop('checked')) {
		node.checked = true;
		var parentNode = node._parent;
		while(parentNode != null) {
			if(_this.hasCheckBox(parentNode)) {
				_this.setNodeChecked(parentNode, true, false);
			}
			parentNode = parentNode._parent;
		}
	} else {
		//如果check-off,所有子off
		node.checked = false;
		_this.setNodeChecked(node, false, true);
	}
	
	if(_this.hasOwnProperty("checkChanged")) {
		(_this.checkChanged)(node, _this);
	}
}

htreeview.prototype.checkedNodes = function() {
	var _this = this;
	
	var checkedNodes = [];
	var nodes = _this.nodes;
	nodes.forEach(function(n, idx) {
		_this._collectCheckedNodes(n, checkedNodes);
	});

	return checkedNodes;
}

htreeview.prototype._collectCheckedNodes = function(parent, checkedNodes) {
	var _this = this;

	if(parent.checked) {
		checkedNodes.push(parent);
	}
	
	if(parent.hasChildren()) {
		var nodes = parent.nodes;
		nodes.forEach(function(n, idx) {
			_this._collectCheckedNodes(n, checkedNodes);
		});
	}
}

htreeview.prototype.setNodeChecked = function(node, bChecked, loopChild) {
	var _this = this;
	
	if(_this.hasCheckBox(node)) {
		node.checked = bChecked;
	
		var nodeCbElem = _this.nodeCheckBoxElem(node);
		if(nodeCbElem != null) {
			nodeCbElem.prop("checked", bChecked);
		}
	}
	
	if(loopChild != undefined && loopChild && node.hasChildren()) {
		var nodes = node.nodes;
		nodes.forEach(function(n, idx) {
			if(_this.hasCheckBox(n)) {
				_this.setNodeChecked(n, bChecked, loopChild);
			}
		});
	}
}

htreeview.prototype._onNodeClick = function(nodeElem) {
	var _this = this;
	
	var node = _this.nodeFromNodeId(nodeElem.prop("id"));
	var nodeHandle = _this.nodeHandleElem(node);
	if(nodeHandle != null &&
		event.target.id == nodeHandle.prop("id")) {
		return;
	}
	
	var nodeCb = _this.nodeCheckBoxElem(node);
	if(nodeCb != null &&
		event.target.id == nodeCb.prop("id")) {
		return;
	}
		
	if(_this.hasOwnProperty("nodeClick")) {
		(_this.nodeClick)(node, _this);
	}
}

htreeview.prototype.hide = function() {
	var _this = this;
	var tgtElem = $("#" + _this.target);
	tgtElem.hide();
}