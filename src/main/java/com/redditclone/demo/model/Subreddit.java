package com.redditclone.demo.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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
 * Subreddit is entity class which contains information about
 * community/subreddit which will be used for categorizing or organizing the
 * posts. It contains information such as name,description,list of post
 * associated with it and created date time info.
 * 
 * @author Santhosh Kumar J
 *
 */
public class Subreddit {

	/**
	 * The unique subreddit/community id generated for each reddit post based on
	 * identity generation type strategy (auto incremented) which also act as
	 * primary key for each subreddit.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The name of the subreddit/community. throws exception with message specified
	 * below when value is null or empty/blank.
	 */
	@NotBlank(message = "Subreddit/Community name is required")
	private String name;

	/**
	 * It contains description about the subreddit/community which basically
	 * describes the basic or important or highlighted information about it. This
	 * field value can't be null.
	 *
	 */
	@NotBlank(message = "Description is required")
	private String description;

	/**
	 * It contains list of post associated/related to this/current
	 * subreddit/cummunity.
	 *
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> relatedPosts;

	/**
	 * contains subreddit created date time information in UTC Time Zone/Format.
	 */
	private Instant createdDateTime;

	/**
	 * Contains post author's user information.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

}
