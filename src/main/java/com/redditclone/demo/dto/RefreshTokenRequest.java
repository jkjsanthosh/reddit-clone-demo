package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RefreshTokenRequest class is used to holds the refresh token and related
 * username information which will be used to generate,validate and delete
 * refresh tokens..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

	/** The refresh token. */
	private String refreshToken;

	/** The username. */
	private String username;
}
