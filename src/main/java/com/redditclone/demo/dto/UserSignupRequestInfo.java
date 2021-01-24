package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RegisterRequest class holds information needed to register a new user signup
 * or registration.
 * 
 * @author Santhosh Kumar J
 *
 */
public class UserSignupRequestInfo {
	/**
	 * the new registration email of the user.
	 */
	private String email;

	/**
	 * the new user name of account of the user.
	 */
	private String username;

	/**
	 * the password of the user.
	 */
	private String password;

}
