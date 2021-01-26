package com.redditclone.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder

/**
 * Post is a entity class which contains information about reddit post. It
 * contains information such as name of the post ,post url,description, voting
 * info, created date time info and related posted user/subreddit category.
 * 
 * @author Santhosh Kumar J
 *
 */
public class Post {

	/**
	 * The unique post id generated for each reddit post based on identity
	 * generation type strategy which also act as primary key for each post.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	/**
	 * The name of the post. throws exception with message specified below when
	 * value is null or empty/blank.
	 */
	@NotBlank(message = "Post Name can't be Null or blank/empty")
	private String postName;

	@Nullable
	/**
	 * contains http link url of complete reddit post. This field value can be null.
	 */
	private String postUrl;

	/**
	 * contains description about the post which basically describes the basic or
	 * important or highlighted information about it. This saved as large object in
	 * database. This field value can be null.
	 * 
	 */
	@Nullable
	@Lob
	private String description;

	/**
	 * no of votes for this post.
	 */
	private Integer voteCount;

	/**
	 * Contains post author's user information.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	/**
	 * related subreddit under which reddit post is created.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subredditId", referencedColumnName = "id")
	private Subreddit relatedSubreddit;

	/**
	 * contains post created date time information in UTC Time Zone/Format.
	 */
	private Instant createdDateTime;

}
