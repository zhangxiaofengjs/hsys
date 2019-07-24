package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.ProjectBusiness;
import com.hsys.business.UserBusiness;
import com.hsys.business.forms.ProjectJsonDeleteForm;
import com.hsys.business.forms.ProjectJsonGetForm;
import com.hsys.business.forms.ProjectJsonUpdateForm;
import com.hsys.business.forms.ProjectLeaderAddForm;
import com.hsys.business.forms.ProjectLeaderDeleteForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.ProjectModel;
import com.hsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	@Autowired
    private ProjectBusiness projectBusiness;

	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse jsonList() {
		try {
			List<ProjectModel> cs = projectBusiness.getProjects();
			return JsonResponse.success().put("projects", cs);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/addLeader")
	@ResponseBody
	public JsonResponse addLeader(@RequestBody ProjectLeaderAddForm form) {
		try {
			projectBusiness.addLeader(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/deleteLeader")
	@ResponseBody
	public JsonResponse deleteLeader(@RequestBody ProjectLeaderDeleteForm form) {
		try {
			projectBusiness.deleteLeader(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/addProject")
	@ResponseBody
	public JsonResponse addProject(@RequestBody ProjectModel project) {
		try {
			projectBusiness.addProject(project);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/deleteProjects")
	@ResponseBody
	public JsonResponse deleteProjects(@RequestBody ProjectJsonDeleteForm form) {
		try {
			projectBusiness.deleteProjects(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/getProject")
	@ResponseBody
	public JsonResponse getProject(@RequestBody ProjectJsonGetForm form) {
		try {
			ProjectModel project = projectBusiness.getProject(form);
			return JsonResponse.success().put("project", project);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/updateProject")
	@ResponseBody
	public JsonResponse updateProject(@RequestBody ProjectJsonUpdateForm form) {
		try {
			projectBusiness.updateProject(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/unleaderList")
	@ResponseBody
	public JsonResponse getUnleaderList(@RequestBody ProjectJsonGetForm form) {
		try {
			List<UserModel> list = projectBusiness.getUnleaderList(form);
			return JsonResponse.success().put("unleaderList", list);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
