package com.hsys.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HsysErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping(value = "error")
    public String error(HttpServletRequest request) {
		return "error/error";
//		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if(statusCode == 401){
//            return "/401";
//        }else if(statusCode == 404){
//            return "/404";
//        }else if(statusCode == 403){
//            return "/403";
//        }else{
//            return "/500";
//        }
    }
}
