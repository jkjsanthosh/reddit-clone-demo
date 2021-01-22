package com.redditclone.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * HttpWebSecurityConfig class defines and configures http web security for
 * reddit back end api requests. It configures authentication for each and every
 * api request in order to ensure security authentication of user and data.
 * since session is not used csrf check is disabled.
 * 
 * @author Santhosh Kumar J
 *
 */
@EnableWebSecurity
public class HttpWebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(HttpSecurity httpSecurityConfig) throws Exception {
		httpSecurityConfig.csrf().disable().authorizeRequests().antMatchers("/api/auth/**").permitAll().anyRequest()
				.authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
