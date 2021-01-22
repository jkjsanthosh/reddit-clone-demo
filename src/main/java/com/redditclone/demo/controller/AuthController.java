package com.redditclone.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.dto.UserSignupRequestInfo;
import com.redditclone.demo.service.AuthService;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Santhosh Kumar J
 *
 */
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
	private final AuthService authService;
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserSignupRequestInfo userSignupRequestInfo) {
		authService.signup(userSignupRequestInfo);
		return new ResponseEntity<String>("User Registration is Successful", HttpStatus.OK);
	}
	

}
