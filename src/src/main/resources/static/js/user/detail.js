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
			} else if(id == "e_companyName" ||
				id == "e_school" ||
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
				}
			}
			pDispDiv.after('<div id="{0}_edit_div" class="form-inline;">\
							{3}\
							<a id="{0}_update" class="hsys-a-pointer" href="javascript:void(0)" onclick="doUpdate(this)">\
								<img style="width:16px;" src="{1}" />\
							</a>\
							<a id="{0}_cancel" class="hsys-a-pointer" href="javascript:void(0)" onclick="cancelUpdate(this)">\
								<img style="width:16px;" src="{2}" />\
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
			id =="e_spelling" ||
			id == "e_mail" ||
			id == "e_address" ||
			id == "e_idNumber" ||
			id == "e_major" ||
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
			if(id == "e_companyName") {
				url = "/company/json/list";
			}else if(id == "e_school"){
				url = "/school/json/list";
			}
			else {
				return;
			}
		
			hsys.ajax({
				"url": url,
				"data": {},
				"success": function(data) {
					if(id == "e_companyName") {
						var companies = data.companies;
						var strHtml = "";
						companies.forEach(function(c, idx) {
							strHtml += '<option value="{0}" {2}>{1}</option>'.
							format(c.id, c.name, val==c.id?"selected":"");
						});
						var pSelect = $("#{0}_value".format(id));
						pSelect.children().remove();
						pSelect.append(strHtml);
					}else if(id == "e_school"){
						var schools = data.schools;
						var strHtml = "";
						schools.forEach(function(s, idx) {
							strHtml += '<option value="{0}" {2}>{1}</option>'.
							format(s.id, s.name, val==s.id?"selected":"");
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
	
	$("a[id^=addPositionHistory]").click(function(){
		var hasAdd = $("#confirmAdd")[0];
		if(hasAdd==undefined){
			var currentRows = $("#positionHistoryTable").find("tr").length; 
			var trHTML = $("<tr></tr>");
			//编号
			trHTML.append("<td>"+currentRows+"</td>");
			//日期
			trHTML.append("<td><input type='date' name='e_position_date' class='form-control'></td>");
			//原岗位
			trHTML.append("<td><input type='text' name='e_position_fromPosition' class='form-control'></td>");
			//调入岗位
			trHTML.append("<td><input type='text' name='e_position_toPosition' class='form-control'></td>");
			//备注
			trHTML.append("<td><input type='text' name='e_position_comment' class='form-control'></td>");
			//确认添加按钮
			trHTML.append("<td><a id='confirmAdd' class='hsys-a-pointer' href='javascript:void(0)' onclick='doConfirm()'>\
					<img src='{0}' width=12px/></a></td>".format(hsys.url('/icons/update.png')));
			//取消添加按钮
			trHTML.append("<td><a class='hsys-a-pointer' href='javascript:void(0)' onclick='cancelAdd(this)'>\
					<img src='{0}' width=12px/></a></td>".format(hsys.url("/icons/delete.png")));
			//添加行
			$("#positionHistoryTable").append(trHTML);
		}
	});
});


function editPositionHistory(pA){
	var self = $(pA);
	var rowId = self.attr("rowId");
	var postId = self.attr("postId");
	var tds = self.parents('tr').children('td').children('span[id*="_disp"]');
	var ids = [];
	tds.each(function(){
		ids.push($(this).attr('id')) 
	})
	if($("#e_position_date_edit{0}".format(rowId)).length == 0){
		for(var i =0; i<ids.length; i++){
			var id = ids[i];
			$("#{0}".format(id)).hide();
			var text = $("#{0}".format(id)).text();
			var editId = id.replace(/_disp/,"_edit");
			var inputId = id.replace(/_disp/,"_input");
			if(id == "e_position_date_disp{0}".format(rowId)){
				$("#{0}".format(id)).after('<span id="{0}"><input id="{1}" type="date" class="form-control"></span>'.format(editId,inputId));
				$("#{0}".format(inputId)).val(text);
			}else if(id == "e_position_fromPosition_disp{0}".format(rowId)||
					id == "e_position_toPosition_disp{0}".format(rowId)||
					id == "e_position_comment_disp{0}".format(rowId)){
				$("#{0}".format(id)).after('<span id="{0}"><input id="{1}" type="text" class="form-control"></span>'.format(editId,inputId));
				$("#{0}".format(inputId)).val(text);
			}
		}
		$("#editPositionHistory{0}".format(rowId)).hide();
		$("#editPositionHistory{0}".format(rowId)).after('<span id="positionHistoryUpdate{0}">\
				<a postId="{1}" editRowId="{0}" class="hsys-a-pointer" href="javascript:void(0)" onclick="doPositionHistoryUpdate(this)">\
				<img src="{2}" width=12px/></a></span>'.format(rowId,postId,hsys.url('/icons/update.png')));
		
		$("#deletePositionHistory{0}".format(rowId)).hide();
		$("#deletePositionHistory{0}".format(rowId)).after('<span id="cancelPositionHistoryUpdate{0}">\
				<a editRowId="{0}" class="hsys-a-pointer" href="javascript:void(0)" onclick="cancelPositionHistoryUpdate(this)">\
				<img src="{1}" width=12px/></a></span>'.format(rowId,hsys.url('/icons/cancel.png')));
	}else{
		for(var i =0; i<ids.length; i++){
			var id = ids[i];
			$("#{0}".format(id)).hide();
			var editId = id.replace(/_disp/,"_edit");
			$("#{0}".format(editId)).show();
		}
		$("#editPositionHistory{0}".format(rowId)).hide();
		$("#positionHistoryUpdate{0}".format(rowId)).show();
		
		$("#deletePositionHistory{0}".format(rowId)).hide();
		$("#cancelPositionHistoryUpdate{0}".format(rowId)).show();
	}
}

function cancelPositionHistoryUpdate(pA) {
	var self = $(pA);
	var rowId = self.attr("editRowId");
	var tds = self.parents('tr').children('td').children('span[id*="_edit"]');
	var ids = [];
	tds.each(function(){
		ids.push($(this).attr('id')) 
	})
	for(var i =0; i<ids.length; i++){
		var id = ids[i];
		if(id == "e_position_date_edit{0}".format(rowId)||
			id == "e_position_fromPosition_edit{0}".format(rowId)||
			id == "e_position_toPosition_edit{0}".format(rowId)||
			id == "e_position_comment_edit{0}".format(rowId)){
			$("#{0}".format(id)).hide();
			$("#{0}".format(id).replace(/_edit/,"_disp")).show();
		}
	}
	
	$("#editPositionHistory{0}".format(rowId)).show();
	$("#positionHistoryUpdate{0}".format(rowId)).hide();
	
	$("#deletePositionHistory{0}".format(rowId)).show();
	$("#cancelPositionHistoryUpdate{0}".format(rowId)).hide();
}


function cancelUpdate(pA) {
	var self = $(pA);
	var id = self.attr("id");
	id = id.left(id.length - "_cancel".length);
	
	if(id == 'e_role') {
		//checkbox 设为可编辑即不可
		$(":checkbox[name='e_role_value']").each(function() {
			$(this).attr("onclick", "return false;");
		});
	}
	
	$("#{0}_disp_div".format(id)).show();
	$("#{0}_edit_div".format(id)).hide();
}

function doConfirm() {
	var date = $(":input[name='e_position_date']").val();
	var fromPosition = $(":input[name='e_position_fromPosition']").val();
	var toPosition = $(":input[name='e_position_toPosition']").val();
	var comment = $(":input[name='e_position_comment']").val();
	if(date=="")date=null;
	
	hsys.ajax({
		"url":"/user/json/addPositionHistory",
		"data": {
			"userId": $("input[name='e_user_id']").val(),
			"date": date,
			"fromPosition": fromPosition,
			"toPosition": toPosition,
			"comment": comment
		},
		"success": function(data) {
			hsys.refresh();
		},
		"error": function(data) {
			hsys.error(data.msg);
		}
	});
}

function doPositionHistoryUpdate(pA) {
	var self = $(pA);
	var rowId = self.attr("editRowId");
	var postId = self.attr("postId");
	var date = $(":input[id='e_position_date_input{0}']".format(rowId)).val();
	var fromPosition = $(":input[id='e_position_fromPosition_input{0}']".format(rowId)).val();
	var toPosition = $(":input[id='e_position_toPosition_input{0}']".format(rowId)).val();
	var comment = $(":input[id='e_position_comment_input{0}']".format(rowId)).val();

	var val;
	hsys.ajax({
		"url":"/user/json/updatePositionHistory",
		"data": {
			"id": postId,
			"date": date,
			"fromPosition": fromPosition,
			"toPosition": toPosition,
			"comment": comment
		},
		"success": function(data) {
			hsys.refresh();
		},
		"error": function(data) {
			hsys.error(data.msg);
		}
	});
}

function cancelAdd(pA) {
	var currentRows = $("#positionHistoryTable").find("tr").length; 
	$("#positionHistoryTable tr:eq({0})".format(currentRows-1)).remove();
}

function deletePositionHistory(pA) {
	var self = $(pA);
	var postId = self.attr("postId");
	hdlg.showYesNo(
		"确定删除此岗位变更信息吗?",
		function() {
			hsys.ajax({
				"url":"/user/json/deletePositionHistory",
				"data": {
					"id": postId
				},
				"success": function(data) {
					hsys.refresh();
				},
				"error": function(data) {
					hsys.error(data.msg);
				}
			});
		}
	);
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
