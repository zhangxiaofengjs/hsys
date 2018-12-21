package com.hsys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HsysApplication {
	protected static final Logger logger = LoggerFactory.getLogger(HsysApplication.class);

	public static void main(String[] args) {
		logger.info("hsys starting ...");
		
		SpringApplication app = new SpringApplication(HsysApplication.class); 
        @SuppressWarnings("unused")
		ConfigurableApplicationContext configAppContext = app.run(args);

        logger.info("\n"+
        		"-----------------------------------\n"+
        		"*                                 *\n"+
        		"*        !!!hsys started!!!       *\n"+
        		"*                                 *\n"+
        		"-----------------------------------\n");
	}
}
