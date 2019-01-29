package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hsys.business.AttendanceBusiness;
import com.hsys.business.forms.AttendanceForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.AttendanceModel;

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
	public JsonResponse upload(@RequestParam("file") MultipartFile[] files) {
		try {
			attendanceBusiness.upload(files);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/html/list")
	public String htmlList(AttendanceForm attendanceForm, Model model) {
		List<AttendanceModel> list = attendanceBusiness.getAttendances(attendanceForm);
		model.addAttribute("list", list);
		return "attendance/list";
	}
}
