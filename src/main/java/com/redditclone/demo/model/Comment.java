package com.redditclone.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
/**
 * Comment class holds information about the comments posted by the user. It
 * contains information such as comment's text, date time,related post and the
 * user information.
 * 
 * @author Santhosh Kumar J
 * 
 *
 */
public class Comment {

	/**
	 * The unique comment id generated for each comment based on identity generation
	 * type strategy (auto incremented) which also act as primary key for each
	 * comment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The commented text which contains content of the comment. */
	@NotEmpty
	private String commentedText;

	/**
	 * the post on which comment is made.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private Post post;

	/**
	 * the user information who commented on the post.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	/**
	 * contains user commented date time information in UTC Time Zone/Format.
	 */
	private Instant commentedDateTime;
}
