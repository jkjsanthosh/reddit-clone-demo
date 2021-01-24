
package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationResponse holds authentication token related login username
 * which will be used by client to authenticate each backend api requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

	/** The username of the user for which authentication token is linked. */
	private String username;

	/**
	 * The authentication token which will be used for authenticating upcoming api
	 * requests.
	 */
	private String authenticationToken;;
}
