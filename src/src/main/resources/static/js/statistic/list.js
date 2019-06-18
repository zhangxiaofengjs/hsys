$(function () {
	$("#approveType_chkbox").click(function(){
		var _this = $(this);
		
		if(_this.prop("checked")) {
			$("#approveType").val(0);
		} else {
			$("#approveType").val(1);
		}
	});
});