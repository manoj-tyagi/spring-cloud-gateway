package com.poc.gateway.exception;

public enum ErrorCodes implements ErrorCode {
	UNEXPECTED_ERROR("EGTWY01"),
	SERVICE_DOWN_ERROR("EGTWY02");

	private String code;
	
	private ErrorCodes(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

}
