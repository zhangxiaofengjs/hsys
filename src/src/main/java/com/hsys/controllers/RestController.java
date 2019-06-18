package com.hsys.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.RestBusiness;
import com.hsys.business.beans.RestListBean;
import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonAddForm;
import com.hsys.business.forms.RestJsonApproveForm;
import com.hsys.business.forms.RestJsonCancelRequestForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonGetForm;
import com.hsys.business.forms.RestJsonRejectForm;
import com.hsys.business.forms.RestJsonUpdateForm;
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
	public String htmlList(RestHtmlListForm form, Model model) {
		List<RestModel> list = restBusiness.getRests(form);
		RestListBean bean = restBusiness.getRestListBean(form);
		model.addAttribute("bean", bean);
		model.addAttribute("form", form);
		model.addAttribute("list", list);
		return "rest/list";
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody RestJsonAddForm form) {
		try {
			restBusiness.add(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(@RequestBody RestJsonGetForm form) {
		try {
			RestModel rest = restBusiness.getRest(form);
			return JsonResponse.success().put("rest", rest);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody RestJsonUpdateForm form) {
		try {
			restBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody RestJsonDeleteForm form) {
		try {
			restBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}

	@RequestMapping("/json/approve")
	@ResponseBody
	public JsonResponse approve(@RequestBody RestJsonApproveForm form) {
		try {
			restBusiness.approve(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/reject")
	@ResponseBody
	public JsonResponse approve(@RequestBody RestJsonRejectForm form) {
		try {
			restBusiness.reject(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}

	@RequestMapping("/json/cancelrequest")
	@ResponseBody
	public JsonResponse cancelRequest(@RequestBody RestJsonCancelRequestForm form) {
		try {
			restBusiness.cancelRequest(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
