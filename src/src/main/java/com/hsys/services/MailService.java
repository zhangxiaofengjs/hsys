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
	class SendMailThread extends Thread {
		private final MineMail mail;
		private final JavaMailSender sender;
		
		public SendMailThread(JavaMailSender sender, MineMail mail) {
			this.mail = mail;
			this.sender = sender;
		}
		
		@Override
		public void run() {
			MimeMessage mimeMailMessage = sender.createMimeMessage();
	    	
	        MimeMessageHelper mimeMessageHelper;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
		        mimeMessageHelper.setFrom(mail.getFrom());
		        mimeMessageHelper.setTo(mail.getTo().toArray(new String[0]));
		        mimeMessageHelper.setSubject(mail.getSubject());
		        mimeMessageHelper.setText(mail.getMessage(), true);
		        
		        //文件
		        for(int i = 0; i < mail.attachmentSize(); i++) {
		        	File file = mail.getAttachment(i);
		        	mimeMessageHelper.addAttachment(file.getName(), file);
		        }
		        
		        //静态资源如图片
		        //body: <img src="cid:testpic" />
//				        FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
//			            mimeMessageHelper.addInline("testpic", file);
		        
		        //thymleaf模板
//				        Context context = new Context();
//				        context.setVariable("id", "006");
//				        String emailContent = templateEngine.process("emailTemplate", context);

		        javaMailSender.send(mimeMailMessage);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	public MailService() {
	}
	
	public void send(SimpleMail sm) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sm.getFrom());
        simpleMailMessage.setTo(sm.getTo().toArray(new String[0]));
        simpleMailMessage.setSubject(sm.getSubject());
        simpleMailMessage.setText(sm.getMessage());
        javaMailSender.send(simpleMailMessage);
	}
	
	public void sendAnsy(MineMail mm) {
		//这里必须启动一个线程去发送，避免阻塞主界面那边的流程
		SendMailThread sendMailThread = new SendMailThread(this.javaMailSender, mm);
		sendMailThread.start();
	}
}
