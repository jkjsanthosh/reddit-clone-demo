package com.redditclone.demo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.redditclone.demo.dto.PostDto;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.Subreddit;
import com.redditclone.demo.model.User;
import com.redditclone.demo.repository.CommentRepository;

/**
 * PostMapper interface defines the mapper methods to map between to post model
 * and post dto which will be used map struct to generate implementation by
 * annotation processing .
 */
@Mapper(componentModel = "spring")
public abstract class PostMapper {
	/**
	 * The comment repository which is used to fetch the no of comments related to
	 * the post.
	 */
	@Autowired
	private CommentRepository commentRepository;

	/**
	 * mapPostDtoFromModel method map and form new post dto from the post model. The
	 * target field commentCount from PostDto model is mapped to count of related
	 * comments.The target field duration from PostDto model is mapped to time
	 * passed after post is created.
	 * 
	 * @param post the post model which needs to be mapped to dto.
	 * @return the post dto which is mapped from model.
	 */
	@Mapping(target = "subredditName", source = "relatedSubreddit.name")
	@Mapping(target = "username", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostDto mapPostDtoFromModel(Post post);

	/**
	 * mapPostModelFromDto method map and form new post model from the post dto.
	 * target fields [postId] is ignored from the mapping. target field
	 * createdDateTime takes current instant time as mapping value always.
	 *
	 * @param postDto          the post dto which needs to be mapped to model.
	 * @param relatedSubreddit the related subreddit of the post
	 * @param postedUser       the posted user [author of the post]
	 * @return the post which is mapped from dto.
	 */
	@Mapping(target = "postId", ignore = true)
	@Mapping(target = "description", source = "postDto.description")
	@Mapping(target = "voteCount", constant = "0")
	@Mapping(target = "createdDateTime", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "postedUser")
	@Mapping(target = "relatedSubreddit", source = "relatedSubreddit")
	@InheritInverseConfiguration
	public abstract Post mapPostModelFromDto(PostDto postDto, Subreddit relatedSubreddit, User postedUser);

	/**
	 * getCommentCount method get and returns the comment count from the count of
	 * related comments to the given post.
	 *
	 * @param post the post which related comments count need to get.
	 * @return the integer the count of comments related to the post.
	 */
	protected Integer getCommentCount(Post post) {
		return commentRepository.countByPost(post);
	}

	/**
	 * getDuration method calculates the duration of the post using TimeAgo kotin
	 * library function and returns relative time ago text.
	 *
	 * @param post the post for which duration needs to be calculated.
	 * @return the String which shows the relative time ago text. <br>
	 *         example such as [4 days ago,21 hours ago,a minute ago,just now]
	 */
	protected String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDateTime().toEpochMilli());
	}
}
