package com.poc.gateway.context;

import com.poc.gateway.config.ApplicationEnv;

public class ApplicationContextImpl implements ApplicationContext {
	
	private ApplicationEnv applicationEnv;

	@Override
	public ApplicationEnv getApplicationEnv() {
		return applicationEnv;
	}

	@Override
	public void setApplicationEnv(ApplicationEnv applicationEnv) {
		this.applicationEnv = applicationEnv;
	}

}
