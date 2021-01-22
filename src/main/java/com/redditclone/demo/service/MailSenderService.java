package com.redditclone.demo.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.redditclone.demo.exceptions.SpringRedditException;
import com.redditclone.demo.model.NotificationEmailInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MailSenderService class provides method to send mail notification to the
 * user.
 */
@Service
@Slf4j
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailSenderService {

	/** The java mail sender service which is used to send mail. */
	private final JavaMailSender javaMailSender;

	/** The mail content builder service which is used to build mail content. */
	private final MailContentBuilderService mailContentBuilderService;

	/**
	 * sendMailNotification method used to send mail notification to the user for
	 * email verification and activation of account.
	 *
	 * @param notificationEmailInfo the email notification info
	 */
	public void sendMailNotification(NotificationEmailInfo notificationEmailInfo) {
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setFrom("user_verification@reddit.com");
			mimeMessageHelper.setTo(notificationEmailInfo.getRecipientEmailAddress());
			mimeMessageHelper.setSubject(notificationEmailInfo.getMailSubject());
			mimeMessageHelper.setText(
					mailContentBuilderService.buildHtmlMailTemplateText(notificationEmailInfo.getMessageBody()));
		};
		try {
			javaMailSender.send(mimeMessagePreparator);
			log.info("Verification and Account Activation Email Sent Successfully!");
			/**
			 * having doubt in this catch block implementation. we should either log or
			 * re-throw exception. not both at same time. otherwise exception messages will
			 * be repeated.
			 */
		} catch (MailException mailException) {
			log.error("Verification and Account Activation Email Sent failed!"
					+ "\n Please refer below stack trace log for more details" + mailException.getMessage());
			throw new SpringRedditException(
					"Exception occurred when sending mail to " + notificationEmailInfo.getRecipientEmailAddress(),
					mailException);
		}

	}
}
