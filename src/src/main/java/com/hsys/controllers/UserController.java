package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.UserBusiness;
import com.hsys.business.forms.UserHtmlDetailForm;
import com.hsys.business.forms.UserHtmlListForm;
import com.hsys.business.forms.UserJsonUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/25
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
    private UserBusiness userBusiness;

	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody UserModel user) {
		try {
			userBusiness.add(user);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/initpwd")
	@ResponseBody
	public JsonResponse initPwd(@RequestBody UserModel user) {
		try {
			userBusiness.initPwd(user);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody UserJsonUpdateForm userUpdateForm) {
		try {
			userBusiness.update(userUpdateForm);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/html/list")
	public String htmlList(UserHtmlListForm userForm, Model model) {
		List<UserModel> list = userBusiness.getUsers(userForm);
		model.addAttribute("userForm", userForm);
		model.addAttribute("users", list);
		
		return "user/list";
	}
	
	@RequestMapping("/html/basic")
	public String htmlList(Model model) {
		return "user/basic";
	}
	
	@RequestMapping("/html/detail")
	public String detail(UserHtmlDetailForm form, Model model) {
		UserModel user = null;
		try {
			user = userBusiness.getDetail(form);
		} catch(Exception e) {
			user = new UserModel();
			user.setName("不存在用户");
		}
		model.addAttribute("user", user);
		return "user/detail";
	}
	
	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse getUser(UserHtmlListForm userForm) {
		try {
			List<UserModel> list = userBusiness.getUsers(userForm);
			return JsonResponse.success().put("users", list);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
