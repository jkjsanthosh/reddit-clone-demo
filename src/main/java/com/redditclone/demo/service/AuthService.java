package com.redditclone.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.dto.AuthenticationResponse;
import com.redditclone.demo.dto.RefreshTokenRequest;
import com.redditclone.demo.dto.UserSignupRequestInfo;
import com.redditclone.demo.exceptions.RedditException;
import com.redditclone.demo.model.NotificationEmailInfo;
import com.redditclone.demo.model.User;
import com.redditclone.demo.model.VerificationToken;
import com.redditclone.demo.repository.UserRepository;
import com.redditclone.demo.repository.VerificationTokenRepository;
import com.redditclone.demo.security.JwtProvider;

import lombok.AllArgsConstructor;

/**
 * AuthService class is provide service methods prepare and register user
 * details such as user information and verification token information.
 *
 * @author Santhosh Kumar J
 */
@Service
@AllArgsConstructor
public class AuthService {

	/**
	 * The password encoder which is used encrypt the password text given by user.
	 */
	private final PasswordEncoder passwordEncoder;

	/**
	 * The user repository which is used to perform CRUD operations on data from the
	 * user table in the database.
	 */
	private final UserRepository userRepository;

	/**
	 * The verification token repository which is used to store the generated
	 * verification for the new user .
	 */
	private final VerificationTokenRepository verificationTokenRepository;

	/** The mail sender service which is used to send mail notification. */
	private final MailSenderService mailSenderService;

	/** The authentication manager which authenticates active user login. */
	private final AuthenticationManager authenticationManager;

	/** The jwt provider which provides token to authenticate. */
	private final JwtProvider jwtProvider;

	/**
	 * The refresh token service which is used to generate refresh token after jwt
	 * time is expired.
	 */
	private final RefreshTokenService refreshTokenService;

	/** The Constant ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY. */
	public static final String ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY = "\"Thank you for signing up to Reddit, \" +\r\n"
			+ "                \"please click on the below url to activate your account : \" +\r\n"
			+ "                \"http://localhost:8080/api/auth/verifyNewAccount/";

	/**
	 * signup method is used to process the new user registration request and do the
	 * process for it.
	 *
	 * @param userSignupRequestInfo the user signup request information which is
	 *                              used to do the user signup process.
	 */
	@Transactional
	public void doSignupProcess(UserSignupRequestInfo userSignupRequestInfo) {
		User newUserInfo = prepareAndSaveNewUserRegistrationInfo(userSignupRequestInfo);
		sendAccountActivationMailNotification(userSignupRequestInfo, newUserInfo);

	}

	/**
	 * prepareAndSaveNewUserRegistrationInfo methods prepares and saves new user
	 * information into user table from request data provided from the user via
	 * signup form input.
	 *
	 * @param userSignupRequestInfo the user signup request information which is
	 *                              used to prepare and save new user account
	 *                              details.
	 */
	private User prepareAndSaveNewUserRegistrationInfo(UserSignupRequestInfo userSignupRequestInfo) {
		User newUserInfo = new User();
		newUserInfo.setUsername(userSignupRequestInfo.getUsername());
		newUserInfo.setEmail(userSignupRequestInfo.getEmail());
		newUserInfo.setPassword(passwordEncoder.encode(userSignupRequestInfo.getPassword()));
		newUserInfo.setCreatedDateTime(Instant.now());
		newUserInfo.setUserEnabled(false);
		userRepository.save(newUserInfo);
		return newUserInfo;
	}

	/**
	 * sendAccountActivationMailNotification method sends account activation mail
	 * notification for newly registered user.
	 *
	 * @param userSignupRequestInfo the user signup request information which will
	 *                              be used to send account verification
	 *                              notification mail.
	 * @param newUserInfo           the new user information which will be used to
	 *                              link the verification token to related user.
	 */
	private void sendAccountActivationMailNotification(UserSignupRequestInfo userSignupRequestInfo, User newUserInfo) {
		NotificationEmailInfo notificationEmailInfo = new NotificationEmailInfo();
		String verificationToken = generateAndSaveNewUserVerificationToken(newUserInfo);
		notificationEmailInfo.setMailSubject("Please activate your new reddit account");
		notificationEmailInfo.setMessageBody(ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY + verificationToken);
		notificationEmailInfo.setRecipientEmailAddress(userSignupRequestInfo.getEmail());
		mailSenderService.sendMailNotification(notificationEmailInfo);
	}

	/**
	 * generateAndSaveNewUserVerificationToken methods generate and save
	 * verification token for new user in verification token table.
	 *
	 * @param relatedUser the related user details for which generated verification
	 *                    token is saved.
	 * @return the verification token.
	 */
	private String generateAndSaveNewUserVerificationToken(User relatedUser) {
		String verificationTokenUUIDString = UUID.randomUUID().toString();
		VerificationToken newUserVerificationToken = new VerificationToken();
		newUserVerificationToken.setToken(verificationTokenUUIDString);
		newUserVerificationToken.setRelatedUser(relatedUser);
		verificationTokenRepository.save(newUserVerificationToken);
		return verificationTokenUUIDString;
	}

	/**
	 * verifyNewUserAccount method verifies the new user account by verification
	 * token and enables if it is valid token.
	 *
	 * @param verificationToken the verification token which will be used to verify
	 *                          the new user account.
	 */
	public void verifyNewUserAccount(String verificationToken) {
		Optional<VerificationToken> verificationTokenInfo = verificationTokenRepository.findByToken(verificationToken);
		if (verificationTokenInfo.isPresent()) {
			fetchAndEnableUserAccount(verificationTokenInfo.get().getRelatedUser().getUsername());
		} else {
			throw new RedditException("Invalid Verification Token");
		}
	}

	/**
	 * fetchAndEnableUserAccount method fetch user account information by username
	 * and enables the account status.
	 *
	 * @param username the username which needs to enabled.
	 */
	@Transactional
	private void fetchAndEnableUserAccount(String username) {
		Optional<User> relatedUserInfoOptional = userRepository.findByUsername(username);
		if (relatedUserInfoOptional.isPresent()) {
			User relatedUserInfo = relatedUserInfoOptional.get();
			userRepository.updateUserAccountStatusByUserId(relatedUserInfo.getUserId(), true);
		} else {
			throw new RedditException("User account details not found with the name:  " + username);
		}
	}

	/**
	 * verifyLoginRequestAndGetAuthenticationInfo method verify the login request
	 * and returns the authentication response if verification is success.
	 * authentication response contains jwt and refresh token which will be used to
	 * authenticate active logged in user account.
	 *
	 * @param username the user name of the user.
	 * @param password the password of the user.
	 * @return the authentication response information [jwt] which will be used by
	 *         client for upcoming api requests.
	 */
	public AuthenticationResponse verifyLoginRequestAndGetAuthenticationInfo(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String authenticationToken = jwtProvider.generateAuthenticationToken(authentication.getName());
		return AuthenticationResponse.builder().username(username).authenticationToken(authenticationToken)
				.refreshToken(refreshTokenService.generateAndSaveRefreshToken().getToken())
				.expiryDateTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis())).build();
	}

	/**
	 * getCurrentLoggedInUser method get the currently logged in user details from
	 * the security context and the user table. throws UsernameNotFoundException if
	 * related user information is not found.
	 *
	 * throws UsernameNotFoundException if related user information is not found.
	 *
	 * @return the current user who is logged in.
	 * 
	 *
	 */
	@Transactional(readOnly = true)
	public User getCurrentLoggedInUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}

	/**
	 * generateAndGetRefreshToken method generates new authentication token along
	 * with a new refresh using previously generated refresh token to validate
	 * authentication token after token is expired which is generated at initial
	 * login.It will also used to generate and refresh token at regular interval in
	 * order to avoid authentication token becoming invalid.
	 *
	 * @param refreshTokenRequest the refresh token request which contains refresh
	 *                            token and username of the previous authentication
	 *                            token information which will be used to generate
	 *                            new refresh token.
	 * 
	 * @return the authentication response
	 */
	public AuthenticationResponse generateAndGetRefreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String authenticationToken = jwtProvider.generateAuthenticationToken(refreshTokenRequest.getUsername());
		return AuthenticationResponse.builder().authenticationToken(authenticationToken)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiryDateTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
				.username(refreshTokenRequest.getUsername()).build();
	}
}