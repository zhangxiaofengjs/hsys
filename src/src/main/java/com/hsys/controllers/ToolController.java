package com.hsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.ToolsBusiness;
import com.hsys.business.beans.LoadInitialDataBean;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.enums.ROLE;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/08
 */
@Controller
@RequestMapping("/tools")
public class ToolController extends BaseController {
	@Autowired
    private ToolsBusiness toolsBusiness;

	@RequestMapping("/index")
	public String toolIndex() {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			return "error";
		}
		
		return "tools/tools";
	}
	
	@RequestMapping("/load-init-data")
	@ResponseBody
	public JsonResponse upload() {
		try {
//			LoadInitialDataBean bean = new LoadInitialDataBean();
//			bean.setUserFilePath("C:\\hsys\\init\\user.txt");			
//			bean.setAttendanceFilePath("C:\\hsys\\init\\attendance.txt");
//			bean.setRestFilePath("C:\\hsys\\init\\rest.txt");
//			bean.setExtratimeFilePath("C:\\hsys\\init\\extratime.txt");
//			bean.setExpenseFilePath("C:\\hsys\\init\\expense.txt");
//			toolsBusiness.loadInitialData(bean);			
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
