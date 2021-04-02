
package com.redditclone.demo.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationResponse holds authentication token related information along
 * with respective username for which authentication response is generated which
 * will be used by client to authenticate each backend api requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

	/** The username of the user for which authentication token is linked. */
	private String username;

	/**
	 * The authentication token which will be used for authenticating upcoming api
	 * requests.
	 */
	private String authenticationToken;;
	/**
	 * The refresh token which will be generated and used for authenticating
	 * upcoming api requests after initial authentication token is expired.
	 */
	private String refreshToken;

	/** The expiry date time of authentication token. */
	private Instant expiryDateTime;
}
