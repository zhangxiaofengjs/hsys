$(document).ready(function(){
  $("#changePwd").click(function(){
	  var self = $(this);

	  var dlg = hdlg.showForm({
			"title":"修改密码",
			"fields":[
				{
					"id":"password",
					"label":"旧密码",
					"type":"password",
					"required":"required",
				},
				{
					"id":"password2",
					"label":"新密码",
					"type":"password",
					"required":"required",
				},
				{
					"id":"password3",
					"label":"再输入新密码",
					"type":"password",
					"required":"required",
				},
			],
			"url":"/user/json/changepwd",
			"valid": function() {
				if(dlg.elem("password2").val() != dlg.elem("password3").val()) {
					dlg.setError("password2", "两次输入密码不一致");
					dlg.setError("password3", "两次输入密码不一致");
					return false;
				}
				return true;
			},
			"success": function(data) {
				hsys.success(true);
			},
			"error": function(data) {
				hsys.error(data.msg);
			}
		});
  	});
});

