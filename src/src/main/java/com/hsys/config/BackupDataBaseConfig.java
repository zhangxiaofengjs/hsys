package com.hsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangxiaofengjs@163.com
 * @date 2019/07/23
 */
@Component
@ConfigurationProperties(prefix="pdsys.backup.database")  
public class BackupDataBaseConfig {
	private boolean enable;
	private String mysqlBin;
	private String location;
	private String name;
	private String host;
	private String userName;
	private String password;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getMysqlBin() {
		return mysqlBin;
	}

	public void setMysqlBin(String mysqlBin) {
		this.mysqlBin = mysqlBin;
	}
}
