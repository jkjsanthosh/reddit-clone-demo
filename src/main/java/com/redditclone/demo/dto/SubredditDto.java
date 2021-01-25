package com.redditclone.demo.dto;

import com.redditclone.demo.model.Subreddit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDto {

	private Long id;

	private String name;

	private String description;

	private Integer noOfRelatedPosts;

	public static Subreddit formSubRedditFromDto(SubredditDto subredditDto) {
		return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription())
				.build();
	}

	public static SubredditDto formSubRedditDto(Subreddit subreddit) {
		return SubredditDto.builder().id(subreddit.getId()).name(subreddit.getName())
				.description(subreddit.getDescription()).noOfRelatedPosts(subreddit.getRelatedPosts().size()).build();
	}
}
