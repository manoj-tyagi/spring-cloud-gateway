package com.poc.gateway.exception;

public enum ErrorCodes { 
	UNEXPECTED_ERROR("EG1001"),
	AUTHENTICATION_ERROR("EG1002"),
	BAD_REQUEST_ERROR("EG1003"),
	RESOURCE_NOT_FOUND_ERROR("EG1004");
	
	private String code;
	
	ErrorCodes(String code) {
		//checkDuplicate(code);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	private void checkDuplicate(String code) {
		for (ErrorCodes errorCodes : ErrorCodes.values()) {
			if(errorCodes.getCode().equalsIgnoreCase(code)) {
				throw new IllegalArgumentException(String.format("Code '%s' already exists for Enum '%s'", code, errorCodes.name()));
			}
		}
	}
}
