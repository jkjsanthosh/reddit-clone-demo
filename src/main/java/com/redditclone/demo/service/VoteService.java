package com.redditclone.demo.service;

import org.springframework.stereotype.Service;

import com.redditclone.demo.dto.VoteDto;
import com.redditclone.demo.exceptions.PostNotFoundException;
import com.redditclone.demo.exceptions.RedditException;
import com.redditclone.demo.mapper.VoteMapper;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;
import com.redditclone.demo.repository.PostRepository;
import com.redditclone.demo.repository.VoteRepository;

import lombok.AllArgsConstructor;

/**
 * SubredditService which provides service methods to perform all kind of
 * operations on subreddits such as creation and fetch.
 */
@Service
@AllArgsConstructor
public class VoteService {

	/** The vote mapper which used to map between vote model and dto. */
	private VoteMapper voteMapper;

	/**
	 * The vote repository which is used to perform CRUD operations on data from the
	 * vote table in the database.
	 */
	private VoteRepository voteRepository;

	/**
	 * The post repository which is used to find the post information from the post
	 * table in the database on which post vote is made.
	 */
	private final PostRepository postRepository;

	/** The auth service which will be used to get logged in user details */
	private AuthService authService;

	/**
	 * registerVote method register a vote given by user for the respective post. It
	 * also updates the vote count for the respective post
	 * 
	 * For creation of vote it finds and use related post information by post id.
	 * Also it uses logged in user details as input.<br>
	 * 
	 * throws RedditException with error message if user already registered a vote
	 * on same vote type.
	 * 
	 * @param voteRegisterRequest the vote register request which contains details
	 *                            to vote.
	 */
	public void registerVote(VoteDto voteRegisterRequest) {
		User currentLoggedInUser = authService.getCurrentLoggedInUser();
		Post votedPost = postRepository.findById(voteRegisterRequest.getPostId())
				.orElseThrow(() -> new PostNotFoundException(voteRegisterRequest.getPostId().toString()));
		boolean isAlreadyVoted = voteRepository.findTopByPostAndVoteTypeAndUserOrderByVoteIdDesc(votedPost,
				voteRegisterRequest.getVoteType(), currentLoggedInUser).isPresent();
		if (isAlreadyVoted) {
			throw new RedditException("You have already " + voteRegisterRequest.getVoteType() + "D for this post ["
					+ votedPost.getPostName() + "]");
		}
		int updatedVoteCount = votedPost.getVoteCount() + voteRegisterRequest.getVoteType().getVoteValue();
		postRepository.updateVoteCount(votedPost.getPostId(), updatedVoteCount);
		voteRepository.save(voteMapper.mapVoteModelFromDto(voteRegisterRequest, votedPost, currentLoggedInUser));
	}

}
