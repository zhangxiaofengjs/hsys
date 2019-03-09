package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.ProjectBusiness;
import com.hsys.business.forms.ProjectJsonListForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.ProjectModel;

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
	public JsonResponse jsonList(@RequestBody(required = false) ProjectJsonListForm form) {
		try {
			List<ProjectModel> cs = projectBusiness.getProjects(form);
			return JsonResponse.success().put("projects", cs);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
