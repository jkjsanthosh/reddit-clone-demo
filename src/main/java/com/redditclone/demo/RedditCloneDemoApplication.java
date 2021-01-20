package com.redditclone.demo;

import java.time.Instant;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedditCloneDemoApplication {

	public static void main(String[] args) {
		Optional.empty();
		System.out.println(Instant.now());
		SpringApplication.run(RedditCloneDemoApplication.class, args);
	}

}
