package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.CompanyBusiness;
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
}
