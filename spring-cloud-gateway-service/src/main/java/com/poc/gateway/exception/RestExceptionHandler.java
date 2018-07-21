package com.poc.gateway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class RestExceptionHandler implements WebExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseStatusExceptionHandler.class);

	private ObjectMapper objectMapper;

	public RestExceptionHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof Exception) {
			LOGGER.error("Handle error:{}", ex.getMessage(), ex);
			ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);

			try {
				//exchange.getResponse().setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);//for validation error
				exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
				exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

				DataBuffer db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(error));

				// write the given data buffer to the response
				// and return a Mono that signals when it's done
				return exchange.getResponse().writeWith(Mono.just(db));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Mono.empty();
			}
		} else if (ex instanceof EntityNotFoundException) {
			exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);

			// marks the response as complete and forbids writing to it
			return exchange.getResponse().setComplete();
		}
		return Mono.error(ex);
	}
}
