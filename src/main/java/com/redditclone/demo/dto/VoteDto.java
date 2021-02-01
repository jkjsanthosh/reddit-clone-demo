package com.redditclone.demo.dto;

import com.redditclone.demo.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VoteDto class is used to hold and transfer the vote information between
 * client,server and database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDto {

	/** The unique vote id which will be generated after voted */
	private Long voteId;

	/** The vote type. */
	private VoteType voteType;

	/** The post id related to vote request. */
	private Long postId;

	/** The username who've requested vote. */
	private String username;
}
