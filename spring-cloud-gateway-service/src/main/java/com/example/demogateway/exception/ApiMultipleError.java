package com.example.demogateway.exception;

public class ApiMultipleError extends ApiSubError {
	private String message;
	private String errorCode;
	private String developerMessage;

	public ApiMultipleError(AppException ex) {
		this.message = ex.getErrorMessage();
		this.errorCode = ex.getErrorCode().getCode();
		this.developerMessage = ex.getMessage();
	}

	public ApiMultipleError(String message, Throwable ex) {
		this.message = message;	
		this.errorCode = ErrorCodes.UNEXPECTED_ERROR.getCode();
		this.developerMessage = ex.getMessage();
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the developerMessage
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
