package com.redditclone.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.redditclone.demo.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

/**
 * HttpWebSecurityConfig class defines and configures http web security for
 * reddit back end api requests.
 * 
 * @author Santhosh Kumar J
 *
 */
@EnableWebSecurity
@AllArgsConstructor
public class HttpWebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * The user details service which is used by authentication builder manager to
	 * build authentication object.
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * The jwt authentication filter which is used to authenticate and filter every
	 * incoming api request based on jwt.
	 */
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * configure method configures http security authentication for each and every
	 * api request in order to ensure security of user and data. since session is
	 * not used csrf check is disable and also it adds jwt authentication filter to
	 * authenticate every api request to verify active user login.
	 */
	@Override
	public void configure(HttpSecurity httpSecurityConfig) throws Exception {
		httpSecurityConfig.csrf().disable().authorizeRequests().antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/subreddit/**").permitAll().antMatchers("/api/posts/**").permitAll()
				.antMatchers("/api/comments/**").permitAll().antMatchers("/api/votes/**").permitAll().anyRequest()
				.authenticated();
		httpSecurityConfig.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * passwordEncoder bean which will be used to encode password.
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * configureAuthenticationManagerBuilder method configures user details service
	 * instance to the authentication manager builder.
	 *
	 * @param authenticationManagerBuilder the authentication manager builder.
	 * @throws Exception the exception
	 */
	@Autowired
	public void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	/**
	 * authenticationManagerBean which is used to authenticate user login details
	 * and returns authentication information.
	 *
	 * @return authenticationManagerBean the authentication manager which will be
	 *         used for authentication.
	 * @throws Exception the exception
	 */
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
