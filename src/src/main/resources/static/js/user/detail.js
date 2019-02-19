$(document).ready(function(){
	$("a[id^=e_]").click(function(){
		var self = $(this);
		var id = self.attr("id");

		var pDispDiv = $("#{0}_disp_div".format(id));
		if(id != "e_role" ) {
			pDispDiv.hide();
		}
		
		var pEditDiv = $("#{0}_edit_div".format(id));

		if(pEditDiv.length == 0) {
			var editor = "";
			if(id == "e_group" ) {
				editor = '<input type="selecttree" class="form-control" name="{0}_value" id="{0}_value">'.format(id);
			} else if(//id == "e_group" ||
				id == "e_sex" ||
				id == "e_degree") {
				editor = '<select class="form-control" style="display:inline;width:200px;" name="{0}_value" id="{0}_value"></select>'.format(id);
			} else if( id == "e_graduateDate" ||
					id == "e_enterDate" ||
					id == "e_exitDate" ) {
				editor = '<input type="date" name="{0}_value" id="{0}_value" value="" class="form-control" style="display:inline;width:200px;">'.format(id);
			} else if(id == "e_role" ) {
				//nothing
				editor = '';
			} else {
				editor = '<input list="{0}_list" name="{0}_value" id="{0}_value" value="" class="form-control" style="display:inline;width:200px;">'.format(id);
				if( id == "e_major" ) {
					editor += '<datalist id="{0}_list" style="font-family:Microsoft Yahei">\
					        <option value="计算机科学与技术">计算机科学与技术</option>\
					        <option value="软件工程">软件工程</option>\
					        <option value="通信工程">通信工程</option>\
					        <option value="机械自动化">机械自动化</option>\
					        <option value="数学">数学</option>\
							<option value="物理">物理</option>\
				        </datalist>'.format(id);
				} else if( id == "e_school" ) {
					editor += '<datalist id="{0}_list">\
					        <option value="南京大学">南京大学</option>\
							<option value="东南大学">东南大学</option>\
							<option value="同济大学">同济大学</option>\
							<option value="上海大学">上海大学</option>\
							<option value="南通大学">南通大学</option>\
							<option value="南京林业大学">南京林业大学</option>\
							<option value="南京信息工程大学">南京信息工程大学</option>\
							<option value="江苏科技大学">江苏科技大学</option>\
							<option value="盐城工学院">盐城工学院</option>\
					        </datalist>'.format(id);
				}
			}
			pDispDiv.after('<div id="{0}_edit_div" class="form-inline;">\
							{3}\
							<a id="{0}_update" class="hsys-a-pointer" href="javascript:void(0)" onclick="doUpdate(this)">\
								<img src="{1}" />\
							</a>\
							<a id="{0}_cancel" class="hsys-a-pointer" href="javascript:void(0)" onclick="cancelUpdate(this)">\
								<img src="{2}" />\
							</a>\
						</div>'.
						format(id,
								hsys.url('/icons/update.png'),
								hsys.url('/icons/cancel.png'),
								editor));
			
			if(id == "e_group"){
				//egroup 重新初始化下拉框
				htreeview.initPullDown({
					"id":"{0}_value".format(id),
					"css": {
						"display": "inline-block",
						"width": "300px",
					},
					"ajax":{
						"url":'/group/json/children'
					}
				});
			}
		} else {
			pEditDiv.show();
		}
		  
		var val = $("#{0}_disp_div span".format(id)).attr("value");
		if(id == "e_name" ||
			id == "e_place" ||
			id == "e_phoneNumber" ||
			id == "e_mail" ||
			id == "e_address" ||
			id == "e_idNumber" ||
			id == "e_major" ||
			id == "e_school" ||
			id == "e_graduateDate" ||
			id == "e_enterDate" ||
			id == "e_exitDate" ) {
			$("input[name='{0}_value']".format(id)).val(val); 
		} else if(id == "e_sex") {
			var pSelect = $("#{0}_value".format(id));
			pSelect.children().remove();
			pSelect.append('<option value=0 {0}>男</option><option value=1 {1}>女</option>'.
					format(val=="0"?"selected":"", val=="1"?"selected":""));
		} else if(id == "e_role") {
			//checkbox 设为可编辑即可
			$(":checkbox[name='e_role_value']").each(function() {
				$(this).removeAttr("onclick");
			});
		} else if(id == "e_degree") {
			var pSelect = $("#{0}_value".format(id));
			pSelect.children().remove();
			pSelect.append('<option value=0 {0}>大专</option>\
							<option value=1 {1}>本科3</option>\
							<option value=2 {2}>本科2</option>\
							<option value=3 {3}>本科1</option>\
							<option value=4 {4}>硕士</option>\
							<option value=5 {5}>博士</option>'.format(
							val=="0"?"selected":"",
							val=="1"?"selected":"",
							val=="2"?"selected":"",
							val=="3"?"selected":"",
							val=="4"?"selected":"",
							val=="5"?"selected":""));
		} else if(id == "e_group"){
			var dispVal = $("#{0}_disp_div span".format(id)).text();
			htreeview.setPullDownValue(id + "_value", val, dispVal);
		} else {
			var url = null;
			if(id == "e_group") {
				url = "/group/json/list";
			}
			else {
				return;
			}
		
			hsys.ajax({
				"url": url,
				"data": {},
				"success": function(data) {
					if(id == "e_group") {
						var groups = data.groups;
						var strHtml = "";
						groups.forEach(function(group, idx) {
							strHtml += '<option value="{0}" {2}>{1}</option>'.
							format(group.id, group.name, val==group.id?"selected":"");
						});
						var pSelect = $("#{0}_value".format(id));
						pSelect.children().remove();
						pSelect.append(strHtml);
					}
				},
				"error": function(data) {
					hsys.error(data.msg);
				}
			});
		}
	});
});

function cancelUpdate(pA) {
	var self = $(pA);
	var id = self.attr("id");
	id = id.left(id.length - "_cancel".length);
	
	$("#{0}_disp_div".format(id)).show();
	$("#{0}_edit_div".format(id)).hide();
}

function doUpdate(pA) {
	var self = $(pA);
	var id = self.attr("id");
	id = id.left(id.length - "_update".length);

	var val;
	if(id == 'e_role') {
		var list = [];
		$(":checkbox[name='e_role_value']").each(function() {
			if($(this).prop("checked")) {
				list.push($(this).attr("id"));
			}
		});
		val = list.join(",");
	} else {
		val = $("[name='{0}_value']".format(id)).val();
	}
	
	hsys.ajax({
		"url":"/user/json/update",
		"data": {
			"id": $("input[name='e_user_id']").val(),
			"field": id,
			"value": val
		},
		"success": function(data) {
			hsys.refresh();
		},
		"error": function(data) {
			hsys.error(data.msg);
		}
	});
}
