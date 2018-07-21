package com.poc.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
	
		
	@Bean
	RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 2);
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public GlobalFilter addAuthorizationHeaderFilter() {
	    return (exchange, chain) -> {
	    	System.out.println("pre filter");
	    	 ServerHttpRequest request = exchange.getRequest().mutate().header("userId", "USER0001").build();
	        return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
	        	System.out.println("post filter");
	        }));
	    };
	}

	/*@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http.httpBasic().and()
				.csrf().disable()
				.authorizeExchange()
				.pathMatchers("/anything/**").authenticated()
				.anyExchange().permitAll()
				.and()
				.build();
	}

	@Bean
	public MapReactiveUserDetailsService reactiveUserDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
		return new MapReactiveUserDetailsService(user);
	}*/
}
