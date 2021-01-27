package com.redditclone.demo.model;

import java.util.Arrays;

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
	UPVOTE(1),
	/**
	 * Vote Value decremented by 1. Downwards the reddit post value.
	 */
	DOWNVOTE(-1);

	/**
	 * indicates the value associated with each vote type.
	 */
	@Getter
	private int voteValue;

	public static VoteType findVoteType(Integer voteValue) {
		return Arrays.stream(VoteType.values())
				.filter(voteType -> Integer.valueOf(voteType.getVoteValue()).equals(voteValue)).findAny()
				.orElseThrow(() -> new IllegalArgumentException(
						"Vote Type matching given vote value " + voteValue + "is not found"));
		
	}
}
