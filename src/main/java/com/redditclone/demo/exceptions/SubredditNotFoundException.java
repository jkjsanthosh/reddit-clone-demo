package com.redditclone.demo.exceptions;

/**
 * SubredditNotFoundException is custom RuntimeException which will be used to
 * throw an exception when related subreddit information is not found.
 */
public class SubredditNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7370430492670824087L;

	public SubredditNotFoundException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}

	public SubredditNotFoundException(String subredditIdOrName) {
		super("Subreddit Not Found with Id or Name: " + subredditIdOrName);
	}

}
