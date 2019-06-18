package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.SchoolBusiness;
import com.hsys.business.forms.SchoolDeleteForm;
import com.hsys.business.forms.SchoolGetForm;
import com.hsys.business.forms.SchoolUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.SchoolModel;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Controller
@RequestMapping("/school")
public class SchoolController extends BaseController {
	@Autowired
	private SchoolBusiness schoolBusiness;
	
	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse jsonList() {
		try {
			List<SchoolModel> ss = schoolBusiness.getSchools();
			return JsonResponse.success().put("schools", ss);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse addSchool(@RequestBody SchoolModel school) {
		try {
			schoolBusiness.add(school);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse deleteSchool(@RequestBody SchoolDeleteForm form) {
		try {
			schoolBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse updateSchool(@RequestBody SchoolUpdateForm form) {
		try {
			schoolBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}

	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse getSchool(@RequestBody SchoolGetForm form) {
		try {
			SchoolModel school = schoolBusiness.getSchool(form);
			return JsonResponse.success().put("school", school);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
