package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.SchoolBusiness;
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
}
