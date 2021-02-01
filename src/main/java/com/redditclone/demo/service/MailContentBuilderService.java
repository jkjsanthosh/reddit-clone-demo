package com.redditclone.demo.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

/**
 * MailContentBuilderService class provides method to build mail content.
 */
@Service
@AllArgsConstructor
public class MailContentBuilderService {

	/**
	 * The Template engine which is used to process dynamic messages inputs with
	 * respective html template.
	 */
	private final TemplateEngine templateEngine;

	/**
	 * buildHtmlTemp late method builds the html template using template engine from
	 * the verification email message and returns it as plain html text.
	 *
	 * @param notificationEmailMessage the verification email message which needs to
	 *                                 be processed as html template engine.
	 * @return the string
	 */
	String buildHtmlMailTemplateText(String notificationEmailMessage) {
		Context verificationEmailContext = new Context();
		verificationEmailContext.setVariable("verificationEmailMessage", notificationEmailMessage);
		return templateEngine.process("user-email-verification-template", verificationEmailContext);

	}
}
