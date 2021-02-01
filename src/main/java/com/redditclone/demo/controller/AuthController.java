package com.redditclone.demo.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.constants.AuthEndPointConstants;
import com.redditclone.demo.dto.AuthenticationResponse;
import com.redditclone.demo.dto.RefreshTokenRequest;
import com.redditclone.demo.dto.UserSignupRequestInfo;
import com.redditclone.demo.service.AuthService;
import com.redditclone.demo.service.RefreshTokenService;

import lombok.AllArgsConstructor;

/**
 * AuthController class provides api request methods to handle all kind of
 * authentication such as signup,login and verification of user accounts.
 *
 * @author Santhosh Kumar J
 */
@RestController
@RequestMapping(AuthEndPointConstants.AUTH_REQUEST_MAPPING)
@AllArgsConstructor
public class AuthController {

	/**
	 * The auth service which will be used to signup,verify and login authentication
	 * user accounts.
	 */
	private final AuthService authService;

	/**
	 * The refresh token service which is used to delete refresh token in order to
	 * perform logout.
	 */
	private final RefreshTokenService refreshTokenService;

	/**
	 * processSignupRequest api method handles sign request from the user and do the
	 * process of signup with the request data.
	 *
	 * @param userSignupRequestInfo the user signup request info which will be used
	 *                              to do the signup process.
	 * @return the response entity which contains response message to the client
	 *         with respective http status.
	 */
	@PostMapping(AuthEndPointConstants.SIGNUP_REQUEST_MAPPING_METHOD)
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
	@PostMapping(AuthEndPointConstants.LOGIN_REQUEST_MAPPING_METHOD)
	public AuthenticationResponse handleLoginRequest(@RequestParam String username, @RequestParam String password) {
		return authService.verifyLoginRequestAndGetAuthenticationInfo(username, password);
	}

	/**
	 * getRefreshToken method handle request to generate and return a refreshed new
	 * authentication token information to maintain active login.
	 *
	 * @param generateRefreshTokenRequest the refresh token request the previous
	 *                                    authentication token information which
	 *                                    will be used to generate new refresh
	 *                                    token.
	 * @return the authentication response which fcontains refreshed authentication
	 *         token information related to requested user.
	 */
	@PostMapping(AuthEndPointConstants.REFRESH_TOKEN_REQUEST_MAPPING_METHOD)
	public AuthenticationResponse getRefreshToken(@Valid @RequestBody RefreshTokenRequest generateRefreshTokenRequest) {
		return authService.generateAndGetRefreshToken(generateRefreshTokenRequest);
	}

	/**
	 * handleLogoutRequest method handle logout request from the user and to make
	 * login inactive or invalid by deleting refresh token.
	 *
	 * @param deleteRefreshTokenRequest the refresh token request which contains
	 *                                  refresh token of the previous authentication
	 *                                  token information.
	 * @return the authentication response which contains authentication token
	 *         related to requested user.
	 */
	@PostMapping(AuthEndPointConstants.lOG_OUT_REQUEST_MAPPING_METHOD)
	public ResponseEntity<String> handleLogoutRequest(
			@Valid @RequestBody RefreshTokenRequest deleteRefreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(deleteRefreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted and User Logged out successfully");
	}
}
