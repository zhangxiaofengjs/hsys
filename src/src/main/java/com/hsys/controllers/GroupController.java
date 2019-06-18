package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.GroupBusiness;
import com.hsys.business.beans.TreeNodeBean;
import com.hsys.business.forms.DeviceJsonUpdateForm;
import com.hsys.business.forms.GroupJsonDeleteForm;
import com.hsys.business.forms.GroupJsonGetForm;
import com.hsys.business.forms.GroupJsonUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.DeviceModel;
import com.hsys.models.GroupModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {
	@Autowired
    private GroupBusiness groupBusiness;

	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse jsonList() {
		try {
			List<GroupModel> groups = groupBusiness.getGroups();
			return JsonResponse.success().put("groups", groups);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/children")
	@ResponseBody
	public JsonResponse jsonNodes(@RequestBody TreeNodeBean bean) {
		try {
			List<TreeNodeBean> groups = groupBusiness.getChildrenGroups(bean);
			return JsonResponse.success().put("nodes", groups);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse addGroup(@RequestBody GroupModel group) {
		try {
			groupBusiness.add(group);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(@RequestBody GroupJsonGetForm form) {
		try {
			GroupModel group = groupBusiness.getGroup(form);
			return JsonResponse.success().put("group", group);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse deleteGroup(@RequestBody GroupJsonDeleteForm form) {
		try {
			groupBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody GroupJsonUpdateForm form) {
		try {
			groupBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
}
