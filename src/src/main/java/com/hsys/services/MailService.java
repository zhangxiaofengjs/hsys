package com.hsys.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hsys.services.beans.MineMail;
import com.hsys.services.beans.SimpleMail;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/04/01
 */
@Service
public class MailService {
	@Autowired
    private JavaMailSender javaMailSender;
	
	public void send(SimpleMail sm) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sm.getFrom());
        simpleMailMessage.setTo(sm.getTo().toArray(new String[0]));
        simpleMailMessage.setSubject(sm.getSubject());
        simpleMailMessage.setText(sm.getMessage());
        javaMailSender.send(simpleMailMessage);
	}
	public void send(MineMail mm) {
    	MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
    	
        MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
	        mimeMessageHelper.setFrom(mm.getFrom());
	        mimeMessageHelper.setTo(mm.getTo().toArray(new String[0]));
	        mimeMessageHelper.setSubject(mm.getSubject());
	        mimeMessageHelper.setText(mm.getMessage(), true);
	        
	        //文件
	        for(int i = 0; i < mm.attachmentSize(); i++) {
	        	File file = mm.getAttachment(i);
	        	mimeMessageHelper.addAttachment(file.getName(), file);
	        }
	        
	        //静态资源如图片
	        //body: <img src="cid:testpic" />
//	        FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
//            mimeMessageHelper.addInline("testpic", file);
	        
	        //thymleaf模板
//	        Context context = new Context();
//	        context.setVariable("id", "006");
//	        String emailContent = templateEngine.process("emailTemplate", context);

	        javaMailSender.send(mimeMailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
