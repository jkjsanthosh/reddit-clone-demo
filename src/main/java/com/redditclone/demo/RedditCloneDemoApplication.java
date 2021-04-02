package com.redditclone.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.redditclone.demo.config.SwaggerConfiguration;

/**
 * RedditCloneDemoApplication class is which is a starting or initial point of
 * reddit clone demo spring boot application.
 */
@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditCloneDemoApplication {

	/**
	 * The main method RedditCloneDemoApplication which triggers spring boot
	 * application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RedditCloneDemoApplication.class, args);
	}

}
