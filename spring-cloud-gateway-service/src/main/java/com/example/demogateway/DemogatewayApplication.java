package com.example.demogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://github.com/spring-cloud-samples/spring-cloud-gateway-sample
 * For Swagger in gateway - look at proxy-service
 * https://github.com/piomin/sample-spring-microservices-new
 * 
 * @author manoj
 *
 */
@RestController
@SpringBootApplication
public class DemogatewayApplication {

	@RequestMapping("/hystrixfallback")
	public String hystrixfallback() {
		return "This is a fallback";
	}

	/*@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("path_route", r -> r.path("/get")
						.uri("http://httpbin.org"))
				.route("host_route", r -> r.host("*.myhost.org")
						.uri("http://httpbin.org"))
				.route("rewrite_route", r -> r.host("*.rewrite.org")
						.filters(f -> f.rewritePath("/foo/(?<segment>.*)","/${segment}"))
						.uri("http://httpbin.org"))
				.route("hystrix_route", r -> r.host("*.hystrix.org")
						.filters(f -> f.hystrix(c -> c.setName("slowcmd")))
								.uri("http://httpbin.org"))
				.route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
						.filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
								.uri("http://httpbin.org"))
				.route("limit_route", r -> r
					.host("*.limited.org").and().path("/anything/**")
						.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
					.uri("http://httpbin.org"))
				.route("websocket_route", r -> r
						.path("/echo")
						.uri("ws://localhost:9000"))
				.route("employees", r -> r.path("/gateway/api/employees")
						 .filters(f -> f.stripPrefix(2))
						.uri("http://localhost:8081/employee/employees"))
				.route("employeesByIdAndName", r -> r.path("/gateway/api/employees/employeeId/name")
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
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(DemogatewayApplication.class, args);
	}
}
