package com.redditclone.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
/**
 * verification token class used to store the authentication token related
 * information which will used for security authentication. It contains
 * information such as authentication token,related user and it's expiry.
 * 
 * @author Santhosh Kumar J
 *
 */
public class VerificationToken {
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
	 * Contains related user information for which verification token is generated.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private User relatedUser;

	/**
	 * contains expiry date time information in UTC Time Zone/Format which will be
	 * used to validate whether token is expiried or not at the time verification of
	 * token .
	 */
	private Instant expiryDateTime;
}
