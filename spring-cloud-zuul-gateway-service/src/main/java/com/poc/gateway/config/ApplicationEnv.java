package com.poc.gateway.config;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.poc.gateway.context.ApplicationContextHolder;
import com.poc.gateway.exception.ErrorCode;

@Component
public class ApplicationEnv {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostConstruct
	public void init() {
		ApplicationContextHolder.getContext().setApplicationEnv(this);
	}
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return env.getProperty(key, defaultValue);
	}
	
	public String getMessage(String key, Object ...args) {
		return messageSource.getMessage(key, args, Locale.US);
	}
	
	public String getMessage(String key, String defaultMessage, Object ...args) {
		return messageSource.getMessage(key, args, defaultMessage, Locale.US);
	}
	
	public String getMessage(ErrorCode code, Object ...args) {
		return getMessage("gateway.error."+code.getCode(), "gateway.error."+code.getCode(), args);
	}

}
