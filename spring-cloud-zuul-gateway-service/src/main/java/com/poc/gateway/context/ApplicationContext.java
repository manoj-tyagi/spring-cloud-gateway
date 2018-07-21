package com.poc.gateway.context;

import com.poc.gateway.config.ApplicationEnv;

public interface ApplicationContext {
	
	public ApplicationEnv getApplicationEnv();
	
	public void setApplicationEnv(ApplicationEnv applicationEnv);
	
}
