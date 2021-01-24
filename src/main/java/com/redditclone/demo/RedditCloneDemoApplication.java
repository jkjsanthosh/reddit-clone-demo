package com.redditclone.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The Class RedditCloneDemoApplication.
 */
@SpringBootApplication
@EnableAsync
public class RedditCloneDemoApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RedditCloneDemoApplication.class, args);
	}

}
