package com.redditclone.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
/**
 * User class contains reddit user information such as
 * name,authentication,communication details and account status.
 * 
 * @author Santhosh Kumar J
 *
 */
public class User {

	/**
	 * The unique user id generated for each reddit post based on identity
	 * generation type strategy (auto incremented) which also act as primary key for
	 * each user.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	/**
	 * The name of the user. throws exception with message specified below when
	 * value is null or empty/blank.
	 */
	@NotBlank(message = "Username is required")
	private String userName;

	/**
	 * contains password of the user which is used authenticate user.
	 */
	@NotBlank(message = "Password is required")
	private String password;

	/**
	 * contains email address of the user which will be used for reddit
	 * communications or notifications and password reset operations
	 */
	@Email
	@NotEmpty(message = "Email is required")
	private String email;

	/**
	 * contains user created date time information in UTC Time Zone/Format.
	 */
	private Instant createdDateTime;

	/**
	 * indicates whether the reddit user account is enabled or not. true, if account
	 * is enabled, false, if account is disabled.
	 */
	private boolean isUserEnabled;

}
