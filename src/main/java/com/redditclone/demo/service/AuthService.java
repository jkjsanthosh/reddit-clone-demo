package com.redditclone.demo.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.redditclone.demo.dto.UserSignupRequestInfo;
import com.redditclone.demo.model.NotificationEmailInfo;
import com.redditclone.demo.model.User;
import com.redditclone.demo.model.VerificationToken;
import com.redditclone.demo.repository.UserRepository;
import com.redditclone.demo.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

/**
 * AuthService class is provide service methods prepare and register user
 * details such as user information and verification token information.
 *
 * @author Santhosh Kumar J
 */
@Service
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

	/**
	 * The password encoder which is used encrypt the password text given by user.
	 */
	private final PasswordEncoder passwordEncoder;

	/**
	 * The user repository which is used to save new signup user details into user
	 * table in database.
	 */
	private final UserRepository userRepository;

	/**
	 * The verification token repository which is used to store the generated
	 * verification for the new user .
	 */
	private final VerificationTokenRepository verificationTokenRepository;

	/** The mail sender service which is used to send mail notification. */
	private final MailSenderService mailSenderService;

	/** The Constant ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY. */
	public static final String ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY = "\"Thank you for signing up to Reddit, \" +\r\n"
			+ "                \"please click on the below url to activate your account : \" +\r\n"
			+ "                \"http://localhost:8080/api/auth/accountVerification/";

	/**
	 * prepareAndSaveNewUserRegistrationInfo methods prepares and saves new user
	 * information into user table from request data provided from the user via
	 * signup form input.
	 *
	 * @param userSignupRequestInfo the user signup request information which is
	 *                              used to prepare and save new user account
	 *                              details.
	 */
	@Transactional
	public void signup(UserSignupRequestInfo userSignupRequestInfo) {
		User newUserInfo = prepareAndSaveNewUserRegistrationInfo(userSignupRequestInfo);
		sendAccountActivationMailNotification(userSignupRequestInfo, newUserInfo);

	}

	/**
	 * sendAccountActivationMailNotification method sends account activation mail
	 * notification for newly registerted user.
	 *
	 * @param userSignupRequestInfo the user signup request info
	 * @param newUserInfo           the new user info
	 */
	private void sendAccountActivationMailNotification(UserSignupRequestInfo userSignupRequestInfo, User newUserInfo) {
		NotificationEmailInfo notificationEmailInfo = new NotificationEmailInfo();
		String verificationToken=generateAndSaveNewUserVerificationToken(newUserInfo);
		notificationEmailInfo.setMailSubject("Please activate your new reddit account");
		notificationEmailInfo.setMessageBody(ACCOUNT_ACTIVATION_MAIL_NOTIFICATION_BODY + verificationToken);
		notificationEmailInfo.setRecipientEmailAddress(userSignupRequestInfo.getEmail());
		mailSenderService.sendMailNotification(notificationEmailInfo);
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
		newUserInfo.setUserName(userSignupRequestInfo.getUserName());
		newUserInfo.setEmail(userSignupRequestInfo.getEmail());
		newUserInfo.setPassword(passwordEncoder.encode(userSignupRequestInfo.getPassword()));
		newUserInfo.setCreatedDateTime(Instant.now());
		newUserInfo.setUserEnabled(false);
		userRepository.save(newUserInfo);
		return newUserInfo;
	}

	/**
	 * generateAndSaveNewUserVerificationToken methods generate and save
	 * verification token for new user in verification token table.
	 *
	 * @param relatedUser the related user details for which generated verification
	 *                    token is saved.
	 * @return the string
	 */
	private String generateAndSaveNewUserVerificationToken(User relatedUser) {
		String verificationTokenUUIDString = UUID.randomUUID().toString();
		VerificationToken newUserVerificationToken = new VerificationToken();
		newUserVerificationToken.setToken(verificationTokenUUIDString);
		newUserVerificationToken.setRelatedUser(relatedUser);
		return verificationTokenUUIDString;
	}
}