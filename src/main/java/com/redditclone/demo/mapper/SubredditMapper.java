package com.redditclone.demo.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.redditclone.demo.dto.SubredditDto;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.Subreddit;

/**
 * SubredditMapper interface defines the mapper methods to map between to
 * subreddit model and subreddit dto which will be used map struct to generate
 * implementation by annotation processing .
 */
@Mapper(componentModel = "spring")
public interface SubredditMapper {

	/**
	 * mapSubredditDtoFromModel method map and form new subreddit dto from the
	 * subreddit model. The target field noOfRelatedPosts from SubredditDto model is
	 * mapped to size of relatedPosts from Subreddit.
	 *
	 * @param subreddit the subreddit model which needs to be mapped to dto.
	 * @return the subreddit dto which is mapped from model.
	 */
	@Mapping(target = "noOfRelatedPosts", expression = "java(mapnoOfRelatedPosts(subreddit.getRelatedPosts()))")
	public SubredditDto mapSubredditDtoFromModel(Subreddit subreddit);

	/**
	 * mapSubredditModelFromDto method map and form new subreddit model from the
	 * subreddit dto. target fields [id,createdDateTime,user,relatedPosts] are
	 * ignored from the mapping.
	 *
	 * @param subredditDto the subreddit dto which needs to be mapped to model.
	 * @return the subreddit which is mapped from dto.
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDateTime", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "relatedPosts", ignore = true)
	@InheritInverseConfiguration
	public Subreddit mapSubredditModelFromDto(SubredditDto subredditDto);

	/**
	 * mapnoOfRelatedPosts method maps the no of related posts from the size of
	 * related post list.
	 *
	 * @param relatedPosts the related posts under subreddit/community.
	 * @return the integer the no of related posts.
	 */
	default Integer mapnoOfRelatedPosts(List<Post> relatedPosts) {
		return relatedPosts.size();
	}
}
