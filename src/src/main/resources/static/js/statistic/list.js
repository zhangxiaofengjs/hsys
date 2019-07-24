$(function () {
	
	htreeview.initPullDown({
		"id":"groupId",
		"css": {
			"display": "inline-block",
			"width": "200px",
		},
		"ajax":{
			"url":'/group/json/children'
		}
	});
	
	
	$("#approveType_chkbox").click(function(){
		var _this = $(this);
		
		if(_this.prop("checked")) {
			$("#approveType").val(0);
		} else {
			$("#approveType").val(1);
		}
	});
	
	$("#download").click(function(){
		var startDate = $("#start").val();
		var endDate = $("#end").val();
		var approveType = $(":hidden[name='approveType']").val();
		var self = $(this);
		var dlg = hdlg.showForm({
			"title":"下载统计记录",
			"fields":[
				{
					"id":"startDate",
					"label":"开始日期",
					"type":"date",
					"required":true,
					"value": startDate
				},
				{
					"id":"endDate",
					"label":"结束日期",
					"type":"date",
					"required":true,
					"value": endDate
				},
				{	
					"id":"approveType",
					"label":"包含未批准项目",
					"type":"checkbox",
					"value": approveType? "1": "0"
				},
				{
					"id":"parent.id",
					"label":"部门",
					"type":"select",
					"options":[],
					"ajax":{
						"url":"/group/json/list",
						"success": function(response, dlg) {
							var field = dlg.field("parent.id");
							response.groups.forEach(function(group,index){
								field.options.push(
									{ 
										"text":group.name,
										"value":group.id,
									}
								);
							});
							dlg.buildField("parent.id");
						},
					},
				},	
				{
					"id":"sumType",
					"label":"按条件下载",
					"type":"radiogroup",
					"options": [
						{
							"text":'按人员',
							"value":0,
						},
						{
							"text":'按组',
							"value":1,
						},
						],
						"value":0,
				},
				
			],
			"buttons":[
				{
					"type":"ok",
					"click": function() {
						dlg.setFootMsg("<img src='{0}'><span class='hsys-blink' style='background-color:#fff'><b>&nbsp;数据在几秒内会下载完毕，请留意...</b></span>".format(hsys.url("/icons/progress-pet.gif")));
					}
				}
			],
			"url": "/statistic/json/download",
			"isDownload": true,
		});
	});
});