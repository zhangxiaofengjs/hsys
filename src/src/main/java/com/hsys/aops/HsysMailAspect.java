package com.hsys.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsys.annotations.HsysMail;
import com.hsys.common.HsysDate;
import com.hsys.models.ExtraTimeModel;
import com.hsys.services.MailService;
import com.hsys.services.beans.MineMail;

@Component
@Aspect
public class HsysMailAspect {
	@Autowired
	MailService mailService;
	
	@Pointcut("@annotation(com.hsys.annotations.HsysMail)")
	private void hsysMail() { 
		
	} 
	
//	@Before("hsysMail()")
//	public void before(JoinPoint joinPoint) {
//		System.out.println("before执行方法");
//	}
	
	@After("hsysMail() && @annotation(hsysMail)")
	public void after(JoinPoint joinPoint, HsysMail hsysMail) {
		Object[] args = joinPoint.getArgs();
		
		MineMail mm = new MineMail();
		mm.setFrom("admin@hinfo.com");
		mm.addTo("admin@hinfo.com");
		mm.setSubject(hsysMail.name());
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<b>%s</b>", hsysMail.name()));
		sb.append("<br>");
		
		if(args != null) {
			for(Object arg : args) {
				sb.append(encode(arg));
			}
		}

	    mm.setMessage(sb.toString());
	        
		mailService.send(mm);
	}
	
	private String encode(Object arg) {
		if(arg instanceof ExtraTimeModel) {
			ExtraTimeModel et = (ExtraTimeModel)arg;
			return String.format("%d, %s, %s, %s, %f, %d, %d",
				et.getUser().getId(),
				HsysDate.format(et.getDate()),
				HsysDate.format(et.getStartTime(), "HH:mm"),
				HsysDate.format(et.getEndTime(), "HH:mm"),
				et.getLength(),
				et.getMealLunch(),
				et.getMealSupper());
		} else {
			ObjectMapper mapper = new ObjectMapper(); 
			try {
				return mapper.writeValueAsString(arg);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
	
//	@Around("hsysMail() && @annotation(hsysMail)")
//	public Object advice(ProceedingJoinPoint joinPoint, HsysMail hsysMail){
//	    System.out.println("环绕通知之开始" + hsysMail.name());
//	    Object ret = null;
//	    try {
//	    	ret = joinPoint.proceed();
//	    } catch (Throwable e) {
//	        e.printStackTrace();
//	    }
//	    System.out.println("环绕通知之结束" + ret);
//	    return ret;
//	}
}
