$(document).ready(function(){
  //先更新一次，10分钟更新一次通知数量
  //updateIndexNoticeCount();
  //setInterval(updateIndexNoticeCount,10*60*1000);//10*60*1000

  $("a[refURL]").click(function(){
	  var self = $(this);

	  showPage(self);
  });
  
  $("#frame_content").load(function(){
	  if($("#frame_content").contents().children().find("div[id=indexPage]").length == 1) {
		  //检测到内侧已经失效重新login则，跳转到之前界面
		  if(prevPageUrl != "") {
			  $("#frame_content").attr('src', prevPageUrl);
		  } else {
			  document.location = hsys.url("/index");
		  }
	  }
	  SetWinHeight(this);
  }); 
  
  $("#changePwd").click(function(){
	  var self = $(this);

	  var dlg = hdlg.showForm({
			"title":"修改密码",
			"fields":[
				{
					"id":"no",
					"type":"hidden",
					"value":$("#userno").val(),
					"required":"required",
				},
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

var prevPageUrl = "";
function showPage(pATag)
{
	var menuId = pATag.attr('id');
	var url = pATag.attr('refURL')
	var menuDisplay = pATag.attr('displayText')
	
	//reset background color
	var menus = $("a[id^='menu_']");
	menus.each(function()
	{
		if( $(this).attr('id') == menuId )
		{
			$(this).addClass('active');
		}
		else
		{
			$(this).removeClass('active');
		}
	});

	var frame = $("iframe[id='frame_content']");
	frame.attr('src', url);
	prevPageUrl = url;

	//更新导航条
	$("#nav_title").html("主页 > " + menuDisplay);
}

function loginUserId() {
	return $("#userid").val();
}

function updateIndexNoticeCount() {
/*	hsys.ajax({
		"url":"/notice/getcount",
		"disableSysError": true,
		"success": function(data) {
			$('#noticeCount').html(data.count==0?'':data.count);
		},
		"error": function(data) {
			$('#noticeCount').html('');
		}
	});*/
}

function initContentHeight(h, framWin) 
{
	if(h<window.screen.availHeight) {
		//h = $(document).height();
		h = window.screen.availHeight;//最小高度設定爲屏幕的高度
	}
	var div = window.parent.document.getElementById('main_div'); 
	div.style.height=(h+30)+"px";//window.document.body.scrollHeight||window.document.body.offsetHeight+5;

	framWin.style.height=(h-30) + "px";//注意减去右侧标题栏高度
} 

function resizeWinHeight()//給子頁面調用
{
	SetWinHeight($("#frame_content")[0]);
}

function SetWinHeight(obj) 
{ 
  var win=obj; 
  var height;
   if (win && !window.opera) 
   { 
     if (win.contentWindow && win.contentWindow.document.body.offsetHeight)   //ie  8 
     { 
        try{ 
			var bheight=win.contentWindow.document.body.scrollHeight; 
			var dheight=win.contentWindow.document.documentElement.scrollHeight; 
			height=Math.max(bheight,dheight); 
		}catch(ex){
			
		} 
		win.style.height = height + "px"; 
     } 
     else if(win.Document && win.Document.body.scrollHeight)//ie  6 
     { 
     	win.style.height = win.Document.body.scrollHeight; 
     } 
   }
   initContentHeight(height, win); //加上右边标题
} 

/*
//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height());
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();
*/
