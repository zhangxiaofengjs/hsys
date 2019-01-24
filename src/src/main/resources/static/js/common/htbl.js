$(document).ready(function(){
	//选择处理
	$("input[name^='select']").click(function(){
		var self = $(this);
		var rowid = self.attr("rowid");
		var checked = self.prop("checked");

		if(self.attr("name")=="selectAll") {
			//全选
			var chkBoxes = $(":checkbox[name='select']");
			chkBoxes.each(function() {
				if(checked) {
					 $(this).prop('checked', true)
				}
				else {
					$(this).removeProp('checked')
				}
			});
			var rows = $("tr");
			rows.each(function() {
				if($(this).attr("rowid") != null) {
					if(checked) {
						$(this).addClass("info");
					} else {
						$(this).removeClass("info");
					}
				}
			});
		} else {
			if(checked) {
				$("tr[rowid='" + rowid + "']").addClass("info");
			} else {
				$("tr[rowid='" + rowid + "']").removeClass("info");
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