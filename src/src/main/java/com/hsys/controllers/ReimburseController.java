package com.hsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsys.business.UserBusiness;
import com.hsys.business.Forms.ReimbuseForm;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Controller
@RequestMapping("/reimburse")
public class ReimburseController extends BaseController {
	@Autowired
    private UserBusiness userBusiness;

	@RequestMapping("/html/main")
    public String listMain(ReimbuseForm reimbuseForm, Model model) {
		if(reimbuseForm.getType() == null) {
			reimbuseForm.setType("items");
		}

		model.addAttribute("form", reimbuseForm);
		model.addAttribute("list", null);
		return "reimburse/main";
	}
}
