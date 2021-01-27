package com.redditclone.demo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.redditclone.demo.dto.PostDto;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.Subreddit;
import com.redditclone.demo.model.User;

/**
 * PostMapper interface defines the mapper methods to map between to post model
 * and post dto which will be used map struct to generate implementation by
 * annotation processing .
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

	/**
	 * mapPostDtoFromModel method map and form new post dto from the post model.
	 *
	 * @param post the post model which needs to be mapped to dto.
	 * @return the post dto which is mapped from model.
	 */
	@Mapping(target = "subredditName", source = "relatedSubreddit.name")
	@Mapping(target = "username", source = "user.username")
	public PostDto mapPostDtoFromModel(Post post);

	/**
	 * mapPostModelFromDto method map and form new post model from the post dto.
	 * target fields [postId,voteCount] is ignored from the mapping. target field
	 * createdDateTime takes current instant time as mapping value always.
	 *
	 * @param postDto          the post dto which needs to be mapped to model.
	 * @param relatedSubreddit the related subreddit of the post
	 * @param postedUser       the posted user [author of the post]
	 * @return the post which is mapped from dto.
	 */
	@Mapping(target = "postId", ignore = true)
	@Mapping(target = "description", source = "postDto.description")
	@Mapping(target = "voteCount", ignore = true)
	@Mapping(target = "createdDateTime", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "postedUser")
	@Mapping(target = "relatedSubreddit", source = "relatedSubreddit")
	@InheritInverseConfiguration
	public Post mapPostModelFromDto(PostDto postDto, Subreddit relatedSubreddit, User postedUser);

}
