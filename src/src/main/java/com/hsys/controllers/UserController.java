package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.UserBusiness;
import com.hsys.business.beans.UserBasicBean;
import com.hsys.business.beans.UserDetailBean;
import com.hsys.business.forms.UserPositionHistoryAddForm;
import com.hsys.business.forms.UserPositionHistoryDeleteForm;
import com.hsys.business.forms.UserPositionHistoryUpdateForm;
import com.hsys.business.forms.UserDownloadForm;
import com.hsys.business.forms.UserHtmlDetailForm;
import com.hsys.business.forms.UserHtmlListForm;
import com.hsys.business.forms.UserJsonChangePwdForm;
import com.hsys.business.forms.UserJsonGetForm;
import com.hsys.business.forms.UserJsonInitPwdForm;
import com.hsys.business.forms.UserJsonUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.HolidayModel;
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
	public JsonResponse initPwd(@RequestBody UserJsonInitPwdForm form) {
		try {
			//同时修改密码，先把id传过去，然后批量更新
			userBusiness.initPwd(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	
	@RequestMapping("/json/changepwd")
	@ResponseBody
	public JsonResponse changePwd(@RequestBody UserJsonChangePwdForm form) {
		try {
			userBusiness.changePwd(form);
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
	public String htmlList(UserHtmlListForm form, Model model) {
		List<UserDetailBean> list = userBusiness.getUsers(form);
		model.addAttribute("form", form);
		model.addAttribute("users", list);
		
		return "user/list";
	}
	
	@RequestMapping("/html/basic")
	public String htmlList(Model model) {
		UserBasicBean bean = userBusiness.getBasicInfo();
		model.addAttribute("bean", bean);
		return "user/basic";
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(HolidayModel holiday,Model model) {
		try {
			List<HolidayModel> list = userBusiness.getHoliday();
			return JsonResponse.success().put("extratime", list);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/html/detail")
	public String detail(UserHtmlDetailForm form, Model model) {
		UserDetailBean user = null;
		try {
			user = userBusiness.getDetail(form);
		} catch(Exception e) {
			user = new UserDetailBean();
			user.setName("不存在用户");
		}
		model.addAttribute("user", user);
		model.addAttribute("form", form);
		return "user/detail";
	}
	
	@RequestMapping("/json/list")
	@ResponseBody
	public JsonResponse getUser(UserJsonGetForm userForm) {
		try {
			List<UserModel> list = userBusiness.getJsonUsers(userForm);
			return JsonResponse.success().put("users", list);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/download")
	@ResponseBody
	public ResponseEntity<byte[]> download(UserDownloadForm form) {
		try {
			return userBusiness.download(form);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/json/addPositionHistory")
	@ResponseBody
	public JsonResponse addPositionHistory(@RequestBody UserPositionHistoryAddForm form) {
		try {
			userBusiness.addPositionHistory(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/updatePositionHistory")
	@ResponseBody
	public JsonResponse updatePositionHistory(@RequestBody UserPositionHistoryUpdateForm form) {
		try {
			userBusiness.updatePositionHistory(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/deletePositionHistory")
	@ResponseBody
	public JsonResponse deletePositionHistory(@RequestBody UserPositionHistoryDeleteForm form) {
		try {
			userBusiness.deletePositionHistory(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
