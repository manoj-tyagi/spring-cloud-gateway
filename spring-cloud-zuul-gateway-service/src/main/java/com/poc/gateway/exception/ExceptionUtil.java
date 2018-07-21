package com.poc.gateway.exception;

import java.net.URL;

import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.poc.gateway.context.ApplicationContextHolder;

public class ExceptionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);
	
	private ExceptionUtil() {

	}
	
	public static GatewayException GatewayException(RequestContext ctx) {
		Throwable throwable = ctx.getThrowable();
		if (throwable.getCause() instanceof ZuulRuntimeException) {
			// this was a failure initiated by one of the local filters
			ZuulRuntimeException ex = (ZuulRuntimeException)throwable.getCause();
			if(throwable.getCause().getCause() instanceof HttpHostConnectException) {
				URL routeHost = ctx.getRouteHost();
				String routeHostPath = routeHost.toString();
				LOGGER.debug("routeHostPath:{}", routeHostPath);
				return new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.SERVICE_DOWN_ERROR, resolveMessage(ErrorCodes.SERVICE_DOWN_ERROR, routeHostPath), throwable.getCause().getCause());
			} else {
				return new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.UNEXPECTED_ERROR,  resolveMessage(ErrorCodes.UNEXPECTED_ERROR), throwable.getCause().getCause());
			}
		}

		if (throwable.getCause() instanceof ZuulException) {
			ZuulException ex = (ZuulException) throwable.getCause();
			return new GatewayException(HttpStatus.valueOf(ex.nStatusCode), ErrorCodes.UNEXPECTED_ERROR,  resolveMessage(ErrorCodes.UNEXPECTED_ERROR), throwable.getCause());
		}

		if (throwable instanceof ZuulException) {
			// exception thrown by zuul lifecycle
			ZuulException ex = (ZuulException) throwable;
			return new GatewayException(HttpStatus.valueOf(ex.nStatusCode), ErrorCodes.UNEXPECTED_ERROR, resolveMessage(ErrorCodes.UNEXPECTED_ERROR), throwable);
		}

		// fallback
		return new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.UNEXPECTED_ERROR, resolveMessage(ErrorCodes.UNEXPECTED_ERROR), throwable);
	}
	
	public static String resolveMessage(ErrorCode code, Object ...args) {
		String message =  ApplicationContextHolder.getContext().getApplicationEnv().getMessage(code, args);
		LOGGER.debug("message:{}", message);
		return message;
	}
}
