package com.example.demogateway.exception;

/**
 * 
 * @author manoj
 *
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 6015336452286279724L;
	private ErrorCodes errorCode = null;
	private String errorMessage = null;

	public AppException(ErrorCodes errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

	}

	public AppException(ErrorCodes errorCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCodes getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}