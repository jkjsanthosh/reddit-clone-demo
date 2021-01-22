package com.redditclone.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * EmailNotificationInfo class contains information needed to notify as email.
 * It contains information such as mail's subject,body and recipient mail
 * address.
 * 
 * @author Santhosh Kumar J
 *
 */
public class NotificationEmailInfo {

	/**
	 * the subject of the email notification.
	 */
	private String mailSubject;

	/**
	 * the email address of the recipient.
	 * 
	 */
	private String recipientEmailAddress;

	/**
	 * the message body of email notification.
	 */
	private String messageBody;
}
