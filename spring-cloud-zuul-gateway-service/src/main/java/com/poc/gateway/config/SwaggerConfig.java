package com.poc.gateway.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	ZuulProperties properties;

	@Primary
	@Bean
	public SwaggerResourcesProvider swaggerResourcesProvider() {
		return () -> {
			List<SwaggerResource> resources = new ArrayList<>();
			properties.getRoutes().values().stream().forEach(
					route -> resources.add(createResource(route.getId(), buildUrl(route.getId()), "2.0")));
			return resources;
		};
	}

	private SwaggerResource createResource(String name, String url, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setUrl(url);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}

	private String buildUrl(String id) {
		return new StringBuilder("/gateway/api/").append(id).append("/v2/api-docs").toString();
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.displayRequestDuration(true)
				.docExpansion(DocExpansion.LIST)
				//Hide the Model
				//.defaultModelsExpandDepth(-1)
				// Disable the validator to avoid "Error" at the bottom of the Swagger UI page
				.validatorUrl(StringUtils.EMPTY) 
				.showExtensions(true)
				.build();
	}
}