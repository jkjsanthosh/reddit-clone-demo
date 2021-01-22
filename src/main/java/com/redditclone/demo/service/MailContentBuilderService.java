package com.redditclone.demo.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

/**
 * The Class MailContentBuilderService.
 */
@Service
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
	 * @param verificationEmailMessage the verification email message which needs to
	 *                                 be processed as html template engine.
	 * @return the string
	 */
	String buildHtmlMailTemplateText(String verificationEmailMessage) {
		Context verificationEmailContext = new Context();
		verificationEmailContext.setVariable("verificationEmailMessage", verificationEmailMessage);
		return templateEngine.process("user-email-verification-template", verificationEmailContext);

	}
}
