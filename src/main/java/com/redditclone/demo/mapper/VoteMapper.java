package com.redditclone.demo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.redditclone.demo.dto.VoteDto;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;
import com.redditclone.demo.model.Vote;

/**
 * VoteMapper interface defines the mapper methods to map between to vote model
 * and vote dto which will be used map struct to generate implementation by
 * annotation processing .
 */
@Mapper(componentModel = "spring")
public interface VoteMapper {
	/**
	 * mapVoteDtoFromModel method map and form new vote dto from the vote model.
	 *
	 * @param vote the vote model which needs to be mapped to dto.
	 * @return the vote dto which is mapped from model.
	 */
	@Mapping(target = "postId", source = "post.postId")
	@Mapping(target = "username", source = "user.username")
	public VoteDto mapVoteDtoFromModel(Vote vote);

	/**
	 * mapVoteModelFromDto method map and form new vote model from the vote dto.
	 * target field vote id is ignored from the mapping.
	 *
	 * @param voteDto   the vote dto which needs to be mapped to model.
	 * @param votedPost the voted post information.
	 * @param votedUser the voted user [author of the vote]
	 * @return the vote which is mapped from dto.
	 */
	@Mapping(target = "voteId", ignore = true)
	@Mapping(target = "user", source = "votedUser")
	@Mapping(target = "post", source = "votedPost")
	@InheritInverseConfiguration
	public Vote mapVoteModelFromDto(VoteDto voteDto, Post votedPost, User votedUser);

}
