package com.hsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.AttendanceBusiness;
import com.hsys.controllers.beans.JsonResponse;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {
	@Autowired
    private AttendanceBusiness attendanceBusiness;

	@RequestMapping("/json/upload")
	@ResponseBody
	public JsonResponse upload() {
		try {
			attendanceBusiness.upload();
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
}
