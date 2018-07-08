package com.example.demogateway.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	private HttpStatus status;
	private int statusCode;
	private String errorCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	private String message="";
	private String developerMessage="";
	private String moreInfoUrl = "/docs/api/";
	
	private List<ApiSubError> errors = new ArrayList<>();

	private ApiError() {
		timestamp = new Date();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
		this.statusCode = status.value();
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.statusCode = status.value();
		this.message = "Unexpected error";
		this.errorCode = ErrorCodes.UNEXPECTED_ERROR.getCode();
		this.developerMessage = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.statusCode = status.value();
		this.message = message;
		//TODO:These are the exceptions thrown from spring framework so error codes needs to be created.
		//For now using ERROR_UNEXPECTED, refer RestExceptionHandler for the exceptions
		this.errorCode = ErrorCodes.UNEXPECTED_ERROR.getCode();
		this.developerMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, AppException ex) {
		this();
		this.status = status;
		this.statusCode = status.value();
		this.message = ex.getErrorMessage();
		this.errorCode = ex.getErrorCode().getCode();
		this.developerMessage = ex.getLocalizedMessage();
	}

	private void addError(ApiSubError subError) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(subError);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public void addError(String message, AppException ex) {
		if(ex != null) {
			errors.add(new ApiMultipleError(message, ex));
		}
	}
	
	public void addError(String message, Throwable ex) {
		if(ex != null) {
			errors.add(new ApiMultipleError(message, ex));
		}
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addError(new ApiValidationError(object, field, rejectedValue, message));
	}

	private void addValidationError(String object, String message) {
		addError(new ApiValidationError(object, message));
	}

	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}

	void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}

	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}

	/**
	 * Utility method for adding error of ConstraintViolation. Usually when
	 * a @Validated validation fails.
	 * 
	 * @param cv the ConstraintViolation
	 */
	private void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError(cv.getRootBeanClass().getSimpleName(),
				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
	}

	void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(this::addValidationError);
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public ApiError setCode(ErrorCodes errorCode) {
		this.errorCode = errorCode.getCode();
		return this;
	}

	public String getMessage() {
		return message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public String getMoreInfoUrl() {
		return moreInfoUrl + this.errorCode;
	}

	public List<ApiSubError> getErrors() {
		return errors;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public ApiError setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public ApiError setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
		return this;
	}
}
