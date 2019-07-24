package com.hsys.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.HsysApplicationContext;
import com.hsys.annotations.HsysMail;
import com.hsys.mail.encoders.IMailEncoder;
import com.hsys.services.MailService;
import com.hsys.services.beans.MineMail;


@Service
//@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE) 加了这个也不能单例，可能是Aspect的特性？？
@Aspect
public class HsysMailAspect {
	@Autowired
	MailService mailService;
	
	@Pointcut("@annotation(com.hsys.annotations.HsysMail)")
	private void hsysMail() { 
	} 
	
	@Before("hsysMail() && @annotation(hsysMail)")
	public void before(JoinPoint joinPoint, HsysMail hsysMail) {
		
	}
	
	@After("hsysMail() && @annotation(hsysMail)")
	public void after(JoinPoint joinPoint, HsysMail hsysMail) throws Exception {
		
	}
	
	@Around("hsysMail() && @annotation(hsysMail)")
	public Object advice(ProceedingJoinPoint joinPoint, HsysMail hsysMail) {
	    //System.out.println("环绕通知之开始" + hsysMail.name());
		IMailEncoder encoder = null;
		try {
			encoder = (IMailEncoder)hsysMail.encoder().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//因为直接new instance，所以还得autowire一下，不然里面的注解不起作用
		HsysApplicationContext.getContext().getAutowireCapableBeanFactory().autowireBean(encoder);

		Object[] args = joinPoint.getArgs();
		encoder.pre(hsysMail.name(), args);

	    Object ret = null;
	    try {
	    	ret = joinPoint.proceed();
	    } catch (Throwable e) {
	        e.printStackTrace();
	    }

	    encoder.post();

	    MineMail mm = encoder.encode();
		mailService.sendAnsy(mm);	
	    return ret;
	}
}
