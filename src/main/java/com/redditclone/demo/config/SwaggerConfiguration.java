package com.redditclone.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration class defines and configures swagger rest api
 * documentation for reddit back end api.
 * 
 * @author Santhosh Kumar J
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	/**
	 * Docket the docket bean which configures the reddit clone related api methods
	 * documentation format which express the content of documentation structure.
	 * 
	 * @return Docket bean with swagger 3 documentation type
	 */
	@Bean
	public Docket redditCloneApiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	/**
	 * getApiInfo method defines and returns the api information for rest api
	 * documentation configuration using docket.
	 *
	 * @return the api info which contains the api information.
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Reddit Clone API").version("1.0")
				.description("API for Reddit Clone Application").contact(new Contact("Santhosh Kumar J", null, null))
				.license("Apche License Version 2.0").build();
	}

}
