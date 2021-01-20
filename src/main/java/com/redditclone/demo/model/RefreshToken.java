package com.redditclone.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/**
 * refresh token class used to store the authentication token related
 * information which will used for security authentication at the time of
 * refresh. It contains information such as authentication token and it's
 * expiry.
 * 
 * @author Santhosh Kumar J
 *
 */
public class RefreshToken {
	/**
	 * The unique token id generated for each verification token based on identity
	 * generation type strategy (auto incremented) which also act as primary key for
	 * each verification token.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * token which will be used for authentication of user.
	 */
	private String token;

	/**
	 * contains refresh token created date time information in UTC Time Zone/Format.
	 */
	private Instant createdDateTime;
}
