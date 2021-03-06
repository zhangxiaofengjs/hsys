package com.hsys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hsys.business.AttendanceBusiness;
import com.hsys.business.beans.HsysPageInfo;
import com.hsys.business.forms.AttendanceDownloadForm;
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
	
	@RequestMapping("/json/download")
	@ResponseBody
	public ResponseEntity<byte[]> download(AttendanceDownloadForm form) {
		try {
			return attendanceBusiness.download(form);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/html/list")
	public String htmlList(AttendanceForm form, Model model) {
		HsysPageInfo<AttendanceModel> list = attendanceBusiness.getAttendances(form);
		model.addAttribute("list", list);
		model.addAttribute("form", form);
		return "attendance/list";
	}
}
