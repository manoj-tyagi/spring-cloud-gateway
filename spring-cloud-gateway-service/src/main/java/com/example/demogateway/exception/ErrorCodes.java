package com.example.demogateway.exception;

public enum ErrorCodes { 
	UNEXPECTED_ERROR("EG1001"),
	AUTHENTICATION_ERROR("EG1002"), 
	BAD_REQUEST_ERROR("EG1003"),
	RESOURCE_NOT_FOUND_ERROR("EG1004");
	
	private String code;
	
	ErrorCodes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
