package com.hsys.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsys.config.HsysConfig;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/21
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	HsysConfig config;
	
	@RequestMapping(value= {"/", "/index"})
    public String index(Model model) {
        return "redirect:user/html/basic";
    }
	
	@RequestMapping("/changenotes")
	public String changenotes(Model model) {
		return "changenotes";
	}
	
	@RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
	
	@RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth != null){
////	        new SecurityContextLogoutHandler().logout(request, response, auth);
//	    }
	    try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
        return "login";
    }
	
	@RequestMapping("/403")
    public String err403(Model model) {
        return "403";
    }
	
	@RequestMapping("/404")
    public String err404(Model model) {
        return "404";
    }
}
