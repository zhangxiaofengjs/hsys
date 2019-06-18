package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.CompanyBusiness;
import com.hsys.business.forms.CompanyJsonDeleteForm;
import com.hsys.business.forms.CompanyJsonGetForm;
import com.hsys.business.forms.CompanyJsonUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.CompanyModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
	@Autowired
    private CompanyBusiness companyBusiness;
	
	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse jsonList() {
		try {
			List<CompanyModel> cs = companyBusiness.getCompanies();
			return JsonResponse.success().put("companies", cs);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse addCompany(@RequestBody CompanyModel company) {
		try {
			companyBusiness.add(company);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse updateCompany(@RequestBody CompanyJsonUpdateForm form) {
		try {
			companyBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse getCompany(@RequestBody CompanyJsonGetForm form) {
		try {
			CompanyModel company = companyBusiness.get(form);
			return JsonResponse.success().put("company", company);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse deleteCompany(@RequestBody CompanyJsonDeleteForm form) {
		try {
			companyBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
