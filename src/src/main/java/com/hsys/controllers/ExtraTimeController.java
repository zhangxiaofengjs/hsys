package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.ExtraTimeBusiness;
import com.hsys.business.forms.ExtraTimeAddForm;
import com.hsys.business.forms.ExtraTimeApprovalForm;
import com.hsys.business.forms.ExtraTimeDeleteForm;
import com.hsys.business.forms.ExtraTimeDownloadForm;
import com.hsys.business.forms.ExtraTimeGetForm;
import com.hsys.business.forms.ExtraTimeListForm;
import com.hsys.business.forms.ExtraTimeUpdateForm;
import com.hsys.business.forms.UserBasicExtraTimeForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.ExtraTimeModel;

/**
 * @author: 18651304595@163.com
 * @version: 2019/01/03
 */
@Controller
@RequestMapping("/extratime")
public class ExtraTimeController extends BaseController {
	@Autowired
	private ExtraTimeBusiness extraTimeBusiness;
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody ExtraTimeAddForm form) {
		try {
			extraTimeBusiness.add(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/addbyuserbasic")
	@ResponseBody
	public JsonResponse addByUserBasic(@RequestBody UserBasicExtraTimeForm extraTime) {
		try {
			extraTimeBusiness.add(extraTime);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody ExtraTimeDeleteForm form) {
		try {
			extraTimeBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody ExtraTimeUpdateForm form) {
		try {
			extraTimeBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}

	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(@RequestBody ExtraTimeGetForm form) {
		try {
			ExtraTimeModel extratime = extraTimeBusiness.getExtratime(form);
			return JsonResponse.success().put("extratime", extratime);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/approval")
	@ResponseBody
	public JsonResponse approval(@RequestBody ExtraTimeApprovalForm form) {
		try {
			//同时批准加班，先把id传过去，然后批量更新
			extraTimeBusiness.approval(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/download")
	@ResponseBody
	public ResponseEntity<byte[]> download(ExtraTimeDownloadForm form) {
		try {
			return extraTimeBusiness.download(form);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/html/list")
	public String htmlList(ExtraTimeListForm form, Model model) {
		List<ExtraTimeModel> list = extraTimeBusiness.getExtraTimes(form);
		model.addAttribute("extratimes", list);
		model.addAttribute("form", form);
		
		return "extratime/list";
	}
}
