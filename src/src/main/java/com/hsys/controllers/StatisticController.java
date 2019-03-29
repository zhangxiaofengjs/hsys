package com.hsys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/11
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController extends BaseController {
	@RequestMapping("/html/list")
	public String htmlList(Model model) {
		model.addAttribute("bean", null);
		return "statistic/list";
	}
}
