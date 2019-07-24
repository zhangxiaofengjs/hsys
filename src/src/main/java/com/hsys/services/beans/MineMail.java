package com.hsys.services.beans;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.hsys.common.HsysList;

public class MineMail {
	private String from;
	private List<String> to;
	private String message;
	private String subject;
	private List<File> attachments;

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
	public void setTo(String string) {
		String[] strs = string.split(";");
		
		List<String> to = HsysList.New();
		Collections.addAll(to, strs);
		
		setTo(to);
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
	public File getAttachment(int index) {
		return attachments.get(index);
	}
	public int attachmentSize() {
		return attachments == null ? 0 : attachments.size();
	}
}
