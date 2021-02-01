package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SubredditDto class is used to hold and transfer the subreddit/community
 * information between client,server and database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDto {

	/**
	 * The unique id of the subreddit/community which will be generated after
	 * subreddit is created.
	 */
	private Long id;

	/** The name of the subreddit/community. */
	private String name;

	/** The description which describes the subreddit/community. */
	private String description;

	/**
	 * The no of related posts which indicates or shows no of posts which is related
	 * to this subreddit/community.
	 */
	private Integer noOfRelatedPosts;
}
