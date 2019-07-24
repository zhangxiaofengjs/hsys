package com.hsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/07/17
 */
@Component
@ConfigurationProperties(prefix="hsys.mail")
public class MailConfig {
	private String from;
	private String to;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
