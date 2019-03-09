package com.hsys;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class HsysApplicationContext {
	private static ApplicationContext context = null;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		HsysApplicationContext.context = context;
	}
	
	public static Resource getResource(String location) {
		return HsysApplicationContext.context.getResource(location);
	}
}
