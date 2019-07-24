$(document).ready(function(){
	//选择处理
	$("input[name^='select']").click(function(){
		var self = $(this);
		var rowid = self.attr("rowid");
		var checked = self.prop("checked");

		var tbl = self.closest("table");
		var selectStyle = "hsys-select-row";
		
		if(self.attr("name")=="selectAll") {
			//全选
			var chkBoxes = $("#{0} :checkbox[name='select']".format(tbl.attr("id")));
			chkBoxes.each(function() {
				if(checked) {
					 $(this).prop('checked', true)
				}
				else {
					$(this).removeProp('checked')
				}
			});
			var rows = $("#"+ tbl.attr("id") + " tr");
			rows.each(function() {
				if($(this).find(":checkbox").attr("name") == "select") {
					if(checked) {
						$(this).addClass(selectStyle);
					} else {
						$(this).removeClass(selectStyle);
					}
				}
			});
		} else {
			if(checked) {
				self.closest("tr").addClass(selectStyle);
			} else {
				self.closest("tr").removeClass(selectStyle);
			}
		}
	});
});

function htbl() {
};

htbl.getSelectedRowId = function(tableId, bAllowMulti, bShowErr) {
	if(!bAllowMulti) {
		bAllowMulti = false;
	}
	if(!bShowErr) {
		bShowErr = true;
	}

	var chkBoxes = $("#{0} :checkbox[name='select']".format(tableId));
	var selectIdArr = [];
	
	chkBoxes.each(function() {
		if($(this).prop('checked') ) {
			selectIdArr.push($(this).attr("rowid"));
		}
	});

	if(bShowErr && selectIdArr.length == 0) {
		hdlg.showOK("请选择要操作的对象。");
		return null;
	}
	
	if(bShowErr && !bAllowMulti && selectIdArr.length != 1) {
		hdlg.showOK("请仅选择一个要操作的对象。");
		return null;
	}

	if(!bAllowMulti) {
		return selectIdArr[0];
	} else {
		return selectIdArr;
	}
}

htbl.checkboxClicked = function(tableId, clickFunc) {
	$("#{0} input[name^='select']".format(tableId)).click(function(){
		(clickFunc)();
	});
	$("#{0} input[name^='selectAll']".format(tableId)).click(function(){
		(clickFunc)();
	});
}

htbl.rowDoubleClicked = function(tableId,funcId){
	$("#{0} tbody".format(tableId)).on('dblclick','tr', function(){
		var selectStyle = "hsys-select-row";
		var rows = $("#{0} tr".format(tableId));
		for (var i = 1; i < rows.length; i++) {
			$(rows[i]).removeClass(selectStyle);
		}
		$(this).addClass(selectStyle);
		var chkBoxes = $("#{0} :checkbox[name='select']".format(tableId));
		$("#{0} :checkbox[name='selectAll']".format(tableId))[0].checked = false;
		for (var i = 0; i < chkBoxes.length; i++) {
			chkBoxes[i].checked = false;
		}
		var checkbox = $('input', this).eq(0);
		checkbox[0].checked = true;
		$("#{0}".format(funcId)).trigger("click");
	});
}
//data={
//	"headers":[
//		{
//			"text":"aaaa"
//		},
//		{
//			"text":"bbbb"
//		}
//	],
//	"rows":[
//		{
//			[{
//				"text":"aaaa"
//			},
//			{
//				"text":"bbbb"
//			}]
//		},
//		{
//			[{
//				"text":"aaaa"
//			},
//			{
//				"text":"bbbb"
//			}]
//		},
//	]
//}
//PdSysTable.buildTable = function(data) {
//	var strHtmlHead = "";
//	data.headers.forEach(function(h, idx) {
//		strHtmlHead += "<th>{0}</th>".format(h.text);
//	});
//	
//	var strHtmlBody = "";
//	data.rows.forEach(function(r, idx) {
//		strHtmlBody += "<tr>";
//		r.cells.forEach(function(c, idx) {
//			strHtmlBody += "<td>{0}</td>".format(c.text);
//		});
//		strHtmlBody += "</tr>";
//	});
//	
//	var strHtml = '<table class="table table-striped table-bordered table-hover table-condensed" contenteditable="false"> \
//				      <thead>\
//						<tr>{0}</tr>\
//					  </thead>\
//					  <tbody>{1}</tbody>\
//				   </table>'.format(strHtmlHead, strHtmlBody);
//	return strHtml;
//};