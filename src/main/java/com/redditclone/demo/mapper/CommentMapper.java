package com.redditclone.demo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.redditclone.demo.dto.CommentDto;
import com.redditclone.demo.model.Comment;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	/**
	 * mapCommentDtoFromModel method map and form new comment dto from the comment
	 * model.
	 *
	 * @param comment the comment model which needs to be mapped to dto.
	 * @return the comment dto which is mapped from model.
	 */
	@Mapping(target = "postId", source = "post.postId")
	@Mapping(target = "commentedUserName", source = "user.username")
	public CommentDto mapCommentDtoFromModel(Comment comment);

	/**
	 * mapCommentModelFromDto method map and form new comment model from the comment
	 * dto. target field id is ignored from the mapping .target field
	 * createdDateTime takes current instant time as mapping value always.
	 *
	 * @param commentDto    the comment dto which needs to be mapped to model.
	 * @param relatedPost   the related post information on which comment is made.
	 * @param commentedUser the commented user [author of the comment]
	 * @return the comment which is mapped from dto.
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "commentedDateTime", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "commentedUser")
	@Mapping(target = "post", source = "relatedPost")
	@InheritInverseConfiguration
	public Comment mapCommentModelFromDto(CommentDto commentDto, Post relatedPost, User commentedUser);

}
