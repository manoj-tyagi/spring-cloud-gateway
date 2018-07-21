package com.poc.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeRouterConfig {
	@Bean 
	public RouteLocator employeeRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("employees", r -> r.path("/gateway/api/employees")
				 .filters(f -> f.stripPrefix(2))
				.uri("http://localhost:8081/employee/employees"))
		.route("employeesByIdAndName", r -> r.path("/gateway/api/employees/employeeId/*/name/*")
				.filters(rw -> rw
						.stripPrefix(2)
						.rewritePath("/employees/employeeId/(?<employeeId>.*)/name/(?<name>.*)", "/employee/employees/employeeId/${employeeId}/name/${name}"))
				.uri("http://localhost:8081"))
		.route("employeesById", r -> r.path("/gateway/api/employees/employeeId/**")
				.filters(rw -> rw
						.stripPrefix(2)
						.rewritePath("/employees/employeeId/(?<employeeId>.*)", "/employee/employees/employeeId/${employeeId}"))
				.uri("http://localhost:8081"))
		.build();
	}
}
