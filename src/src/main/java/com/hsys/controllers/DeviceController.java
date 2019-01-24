package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.DeviceBusiness;
import com.hsys.business.forms.DeviceJsonDeleteForm;
import com.hsys.business.forms.DeviceJsonGetForm;
import com.hsys.business.forms.DeviceJsonUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.DeviceModel;

/**
 * @author: qs
 * @version: 2019/01/18
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {
	@Autowired
    private DeviceBusiness deviceBusiness;

	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody DeviceModel device) {
		try {
			deviceBusiness.add(device);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody DeviceJsonUpdateForm form) {
		try {
			deviceBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(@RequestBody DeviceJsonGetForm form) {
		try {
			DeviceModel device = deviceBusiness.getDevice(form);
			return JsonResponse.success().put("device", device);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody DeviceJsonDeleteForm form) {
		try {
			deviceBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/html/list")
	public String htmlList(Model model) {
		List<DeviceModel> devices = deviceBusiness.getDevices();
		model.addAttribute("devices", devices);
		return "device/list";
	}
}

