package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PostDto class is used to hold and transfer the post information between
 * client,server and database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

	/** The unique post id which will be generated after post is created. */
	private Long postId;

	/** The name of post. */
	private String postName;

	/** The post url which contains http link url of complete reddit post */
	private String postUrl;

	/** The description which describes the post. */
	private String description;

	/** The subreddit name which is related to the post. */
	private String subredditName;

	/** The username who have created [author] this post. */
	private String username;

	/** The vote count number of votes made on this post. */
	private Integer voteCount;

	/** The comment count number of comments made on this post. */
	private Integer commentCount;

	/** The duration of post how much has been passed after post is created. */
	private String duration;

}
