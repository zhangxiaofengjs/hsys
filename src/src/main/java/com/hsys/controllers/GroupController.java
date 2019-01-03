package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.GroupBusiness;
import com.hsys.controllers.beans.JsonResponse;
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
}
