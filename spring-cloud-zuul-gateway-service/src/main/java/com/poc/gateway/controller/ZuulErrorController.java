package com.poc.gateway.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.gateway.dto.GatewayErrorResponse;
import com.poc.gateway.exception.ErrorCodes;
import com.poc.gateway.exception.ExceptionUtil;
import com.poc.gateway.exception.GatewayException;

@RestController()
public class ZuulErrorController implements ErrorController {

	@Value("${error.path:/error}")
	private String errorPath;

	public ZuulErrorController() {
		super();
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}

	@RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<GatewayErrorResponse> error(HttpServletRequest request) {
		GatewayErrorResponse response = new GatewayErrorResponse();
		HttpStatus status = getStatus(request);
		final Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable instanceof GatewayException) {
			GatewayException ex = (GatewayException) throwable;
			response.setStatus(status.value());
			response.setCode(ex.getErrorCode().getCode());
			response.setMessage(ex.getMessage());
			response.setDebugMessage(ex.getErrorCause());
		} else {
			response.setStatus(status.value());
			response.setCode(ErrorCodes.UNEXPECTED_ERROR.getCode());
			response.setMessage(ExceptionUtil.resolveMessage(ErrorCodes.UNEXPECTED_ERROR));
			response.setDebugMessage(throwable == null ? "Unexpected Error" : throwable.getMessage());
		}
		return ResponseEntity.status(status).body(response);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		int status = statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
		return HttpStatus.valueOf(status);
	}

}
