package com.redditclone.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentDto class is used to hold and transfer the comment information between
 * client,server and database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	/** The unique comment id which will be generated after comment is created. */
	private Long id;

	/** The commented text. */
	private String commentedText;

	/** The post id on which comment is made. */
	private Long postId;

	/** The commented user name. */
	private String commentedUserName;

	/**
	 * The duration of comment how much has been passed after comment is created.
	 */
	private String duration;
}
