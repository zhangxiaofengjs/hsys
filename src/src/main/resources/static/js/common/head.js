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
  
  //设定menu展开状态
  var cookies = $.cookie();
  for(var cookie in cookies) {
	  if(cookie.startWith('menu_state_')) {
		  var expanded =  $.cookie(cookie);
		  var hrefId = cookie.substr('menu_state_'.length);
		  var menuA = $("a[href='{0}']".format(hrefId));
		  var menuUl = $("#" + hrefId.substr(1));
		  
		  if(expanded == "true") {
			  menuA.removeClass('collapsed');
			  menuA.attr('aria-expanded', true);
			  
			  menuUl.addClass("in");
			  menuUl.attr('aria-expanded', true);
			  menuUl.css('height', '');
		  } else {
			  menuA.addClass('collapsed');
			  menuA.attr('aria-expanded', false);
			  
			  menuUl.removeClass("in");
			  menuUl.attr('aria-expanded', false);
			  menuUl.css('height', '0px');
		  }
	  }
  }
  
  //保存菜单展开状态
  $("a[href^='#cmn_menu_']").click(function(){
	  var self = $(this);
	  var expanded = self.hasClass('collapsed');
	  
	  $.cookie('menu_state_' + self.attr("href"), expanded, { expires: 7, path: '/' }); 
  	});
});

