package com.hsys.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hsys.business.StatisticBusiness;
import com.hsys.business.forms.StatisticHtmlForm;
import com.hsys.models.StatisticModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/11
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController extends BaseController {	
	@Autowired
	private StatisticBusiness statisticBusiness;
	
	@RequestMapping("/html/list")
	public String htmlList(StatisticHtmlForm form,Model model) {
		List<StatisticModel> list = statisticBusiness.getListStatistics(form);
		model.addAttribute("form", form);
		model.addAttribute("list", list);
		return "statistic/list";
	}
	
}
