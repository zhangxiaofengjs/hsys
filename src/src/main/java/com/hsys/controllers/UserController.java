package com.hsys.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.UserBusiness;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.exception.HsysException;
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

	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse jsonById1(@RequestBody UserModel user) {
		if(user.getNo().equals("1")) {
			UserModel u = new UserModel();
			u.setNo("XXX");
			u.setName("yyy");
			UserModel u1 = new UserModel();
			u1.setNo("XXX1");
			u1.setName("yyy1");
			
			List<UserModel> list = new ArrayList<UserModel>();
			list.add(u1);
			list.add(u);
			
			return JsonResponse.success().put("users", list);
		}
		if(user.getNo().equals("2")) {
			UserModel u = new UserModel();
			u.setNo("XXX");
			u.setName("yyy");
			return JsonResponse.success().put("user", u);
		}
		return JsonResponse.error("sss");
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody UserModel user) {
		try {
			userBusiness.add(user);
		} catch(HsysException e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JsonResponse update(@RequestBody UserModel user) {
		return JsonResponse.success("success");
		//return JsonResponse.error("sss");
	}
	
	@RequestMapping("/html/list")
	public String htmlList(UserModel user, Model model) {
		List<UserModel> list = userBusiness.getAllUser();
		model.addAttribute("users", list);
		
		return "user/list";
	}
}
