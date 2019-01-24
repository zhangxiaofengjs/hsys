package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.RestBusiness;
import com.hsys.business.forms.RestForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.RestModel;

/**
 * @author: 韩才鹏
 * @version: 2019/01/18
 */
@Controller
@RequestMapping("/rest")
public class RestController extends BaseController {
	@Autowired
    private RestBusiness restBusiness;

	@RequestMapping("/html/list")
	public String htmlList(RestForm restForm, Model model) {
		List<RestModel> list = restBusiness.getRests(restForm);
		model.addAttribute("list", list);
		return "rest/list";
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody RestModel rest) {
		try {
			restBusiness.add(rest);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	
}
