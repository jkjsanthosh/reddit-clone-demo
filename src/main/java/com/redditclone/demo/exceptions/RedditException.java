package com.redditclone.demo.exceptions;

/**
 * SpringRedditException is custom RuntimeException which will be used to throw
 * all kind of exceptions happens around reddit back end api operations.
 */
public class RedditException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -384233500693185458L;

	public RedditException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}

	public RedditException(String exMessage) {
		super(exMessage);
	}
}
