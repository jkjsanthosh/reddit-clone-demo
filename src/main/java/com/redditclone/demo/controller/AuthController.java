package com.redditclone.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.dto.AuthenticationResponse;
import com.redditclone.demo.dto.UserSignupRequestInfo;
import com.redditclone.demo.service.AuthService;

import lombok.AllArgsConstructor;

/**
 * AuthController class provides api request methods to handle all kind of
 * authentication such as registration,login and verification of user accounts.
 *
 * @author Santhosh Kumar J
 */
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;

	/**
	 * processSignupRequest api method handles sign request from the user and do the
	 * process of signup with the request data.
	 *
	 * @param userSignupRequestInfo the user signup request info which will be used
	 *                              to do the signup process.
	 * @return the response entity which contains response message to the client
	 *         with respective http status.
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> handleSignupRequest(@RequestBody UserSignupRequestInfo userSignupRequestInfo) {
		authService.doSignupProcess(userSignupRequestInfo);
		return new ResponseEntity<String>("User Registration is Successful", HttpStatus.OK);
	}

	/**
	 * verifyNewUserAccount api request method handles the verification of the new
	 * user account.
	 *
	 * @param verificationToken the verification token which will be used for
	 *                          account verification.
	 * @return the response entity which contains response message to the client
	 *         with respective http status.
	 */
	@GetMapping("verifyNewAccount/{verificationToken}")
	public ResponseEntity<String> verifyNewUserAccount(@PathVariable String verificationToken) {
		authService.verifyNewUserAccount(verificationToken);
		return new ResponseEntity<String>("Account Activated Successfully", HttpStatus.OK);
	}

	/**
	 * handleLoginRequest method handle login request from the user and verify the
	 * login request and also returns authentication response.
	 *
	 * @param username the user name of the user to authenticate.
	 * @param password the password of the user to authenticate.
	 * @return the authentication response which contains authentication token
	 *         related to requested user.
	 */
	@PostMapping("/login")
	public AuthenticationResponse handleLoginRequest(@RequestParam String username, @RequestParam String password) {
		return authService.verifyLoginRequestAndGetAuthenticationInfo(username, password);
	}

}
