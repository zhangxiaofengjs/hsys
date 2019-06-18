package com.hsys.services.beans;

import java.util.List;

import com.hsys.common.HsysList;

public class SimpleMail {
	private String from;
	private List<String> to;
	private String message;
	private String subject;

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void addTo(String string) {
		if(this.to == null) {
			this.to = HsysList.New();
		}
		this.to.add(string);
	}
}
