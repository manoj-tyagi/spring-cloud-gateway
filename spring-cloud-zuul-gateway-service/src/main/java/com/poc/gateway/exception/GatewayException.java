package com.poc.gateway.exception;

import org.springframework.http.HttpStatus;

public class GatewayException extends RuntimeException {
	private static final long serialVersionUID = 438325341984121038L;
	
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	
	private ErrorCode errorCode;
	
	private String errorCause;
	
	public GatewayException(HttpStatus status, ErrorCode errorCode, String message, Throwable th) {
		super(message, th);
		this.status = status;
		this.errorCode = errorCode;
		this.errorCause = th.getMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	
}
