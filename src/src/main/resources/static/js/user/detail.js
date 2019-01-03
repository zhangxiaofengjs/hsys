$(document).ready(function(){
	$("a[id^=e_]").click(function(){
		var self = $(this);
		var id = self.attr("id");

		var pDispDiv = $("#{0}_disp_div".format(id));
		pDispDiv.hide();
		
		var pEditDiv = $("#{0}_edit_div".format(id));

		if(pEditDiv.length == 0) {
			var pTd = pDispDiv.parent();
			var editor = "";
			if(id == "e_group") {
				editor = '<select class="form-control" style="display:inline;width:200px;" name="{0}_value" id="{0}_value"></select>'.
				format(id);
			} else {
				editor = '<input name="{0}_value" id="{0}_value" value="" class="form-control" style="display:inline;width:200px;">'.format(id);
			}
			pTd.append('<div id="{0}_edit_div" style="display:inline-block;">\
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
		} else {
			pEditDiv.show();
		}
		  
		var val = $("#{0}_disp_div span".format(id)).attr("value");
		var url = null;
		if(id == "e_group") {
			url = "/group/json/list";
		}
		if(url == null) {
			$("input[name='{0}_value']".format(id)).val(val); 
		} else {
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

	hsys.ajax({
		"url":"/user/json/update",
		"data": {
			"id": $("input[name='e_user_id'").val(),
			"field": id,
			"value": $("#{0}_value".format(id)).val()
		},
		"success": function(data) {
			hsys.refresh();
		},
		"error": function(data) {
			hsys.error(data.msg);
		}
	});
}
