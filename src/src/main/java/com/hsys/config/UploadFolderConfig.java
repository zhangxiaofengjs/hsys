package com.hsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/07/17
 */
@Component
@ConfigurationProperties(prefix="hsys.upload")
public class UploadFolderConfig {
	private String tempFolder;
	private String attendanceFolder;
	private String receiptAttachmentFolder;
	
	public String getReceiptAttachmentFolder() {
		return receiptAttachmentFolder;
	}

	public void setReceiptAttachmentFolder(String receiptAttachmentFolder) {
		this.receiptAttachmentFolder = receiptAttachmentFolder;
	}

	public String getTempFolder() {
		return tempFolder;
	}

	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}

	public String getAttendanceFolder() {
		return attendanceFolder;
	}

	public void setAttendanceFolder(String attendanceFolder) {
		this.attendanceFolder = attendanceFolder;
	}
}
