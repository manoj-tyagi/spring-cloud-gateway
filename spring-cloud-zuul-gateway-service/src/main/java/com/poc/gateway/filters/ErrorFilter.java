package com.poc.gateway.filters;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.poc.gateway.exception.ExceptionUtil;
import com.poc.gateway.exception.GatewayException;

@Component
public class ErrorFilter extends ZuulFilter {
	
	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";
	protected static final String CUSTOM_ERROR_FILTER_RAN = "customErrorFilter.ran";

	@Value("${error.path:/error}")
	private String errorPath;
	
	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.set(SEND_ERROR_FILTER_RAN, true);
		// only forward to errorPath if it hasn't been forwarded to already
		return ctx.getThrowable() != null
				&& !ctx.getBoolean(CUSTOM_ERROR_FILTER_RAN, false);
	}

	@Override
	public Object run() {
		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			GatewayException exception = ExceptionUtil.GatewayException(ctx);
			HttpServletRequest request = ctx.getRequest();
			request.setAttribute("javax.servlet.error.status_code", exception.getStatus().value());
			request.setAttribute("javax.servlet.error.exception", exception);
			if (StringUtils.hasText(exception.getErrorCause())) {
				request.setAttribute("javax.servlet.error.message", exception.getErrorCause());
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
			if (dispatcher != null) {
				ctx.set(CUSTOM_ERROR_FILTER_RAN, true);
				if (!ctx.getResponse().isCommitted()) {
					ctx.setResponseStatusCode(exception.getStatus().value());
					dispatcher.forward(request, ctx.getResponse());
				}
			}
		}
		catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}
}