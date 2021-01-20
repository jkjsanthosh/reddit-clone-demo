package com.redditclone.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * VoteType Enum contains type of user can votes and it's associated value.
 * 
 * @author Santhosh Kumar J
 *
 */
public enum VoteType {
	/**
	 * Vote Value incremented by 1. Upwards the reddit post value.
	 */
	UPVOTE(voteValue = 1),
	/**
	 * Vote Value decremented by 1. Downwards the reddit post value.
	 */
	DOWNVOTE(voteValue = -1);

	VoteType(int voteValue) {
	}

	/**
	 * indicates the value associated with each vote type.
	 */
	private static int voteValue;
}
