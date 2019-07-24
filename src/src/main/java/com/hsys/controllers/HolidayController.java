package com.hsys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsys.business.DeviceBusiness;
import com.hsys.business.HolidayBusiness;
import com.hsys.business.forms.DeviceHtmlListForm;
import com.hsys.business.forms.DeviceJsonDeleteForm;
import com.hsys.business.forms.DeviceJsonGetForm;
import com.hsys.business.forms.DeviceJsonUpdateForm;
import com.hsys.business.forms.ExtraTimeAddForm;
import com.hsys.business.forms.ExtraTimeDeleteForm;
import com.hsys.business.forms.ExtraTimeGetForm;
import com.hsys.business.forms.ExtraTimeUpdateForm;
import com.hsys.business.forms.HolidayAddForm;
import com.hsys.business.forms.HolidayDeleteForm;
import com.hsys.business.forms.HolidayGetForm;
import com.hsys.business.forms.HolidayHtmlListForm;
import com.hsys.business.forms.HolidayUpdateForm;
import com.hsys.controllers.beans.JsonResponse;
import com.hsys.models.DeviceModel;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.HolidayModel;

/**
 * @author: wu
 * @version: 2019/07/17
 */
@Controller
@RequestMapping("/holiday")
public class HolidayController extends BaseController {
	@Autowired
	private HolidayBusiness holidayBusiness;	
		
	@RequestMapping("/html/list")
	public String htmlList(HolidayHtmlListForm form,Model model) {
		List<HolidayModel> holidays = holidayBusiness.getHolidays(form);
		model.addAttribute("holidays", holidays);
		model.addAttribute("form", form);
		return "holiday/list";
	}
	
	@RequestMapping("/json/add")
	@ResponseBody
	public JsonResponse add(@RequestBody HolidayAddForm form) {
		try {
			holidayBusiness.add(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/update")
	@ResponseBody
	public JsonResponse update(@RequestBody HolidayUpdateForm form) {
		try {
			holidayBusiness.update(form);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
		return JsonResponse.success();
	}
	
	@RequestMapping("/json/get")
	@ResponseBody
	public JsonResponse get(@RequestBody HolidayGetForm form) {
		try {
			HolidayModel holiday = holidayBusiness.getHoliday(form);
			return JsonResponse.success().put("holiday", holiday);
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping("/json/delete")
	@ResponseBody
	public JsonResponse delete(@RequestBody HolidayDeleteForm form) {
		try {
			holidayBusiness.delete(form);
			return JsonResponse.success();
		} catch(Exception e) {
			return JsonResponse.error(e.getMessage());
		}
	}
	

}

