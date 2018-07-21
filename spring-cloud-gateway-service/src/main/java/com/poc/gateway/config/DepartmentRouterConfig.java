package com.poc.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentRouterConfig {
	
	@Bean 
	public RouteLocator departmentRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("departments", r -> r.path("/gateway/api/departments")
				 .filters(f -> f.stripPrefix(2))
				.uri("http://localhost:8081/employee/departments"))
		.route("departmentsById", r -> r.path("/gateway/api/departments/departmentId/**")
				.filters(rw -> rw
						.stripPrefix(2)
						.rewritePath("/departments/departmentId/(?<departmentId>.*)", "/employee/departments/departmentId/${departmentId}"))
				.uri("http://localhost:8081"))
		.build();
	}
}
