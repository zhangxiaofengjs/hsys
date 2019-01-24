package com.hsys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hsys.config.beans.Version;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/22
 */
@Configuration  
public class HsysConfig {
	@Bean  
    @ConfigurationProperties(prefix="hsys.version")  
    public Version getVersion(){  
		return new Version();
    }
	
	
}
