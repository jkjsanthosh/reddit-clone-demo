package com.redditclone.demo.exceptions;

/**
 * PostNotFoundException is custom RuntimeException which will be used to throw
 * an exception when related post information is not found.
 */
public class PostNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5341228400719341453L;

	public PostNotFoundException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}

	public PostNotFoundException(String postIdOrName) {
		super("Post Not Found with Id or Name: " + postIdOrName);
	}
}
