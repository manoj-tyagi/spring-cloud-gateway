package com.poc.gateway.context;

public class ApplicationContextHolder {
	
	private static final ApplicationContext INSTANCE = new ApplicationContextImpl();
	
	public static ApplicationContext getContext() {
		return INSTANCE;
	}

}
