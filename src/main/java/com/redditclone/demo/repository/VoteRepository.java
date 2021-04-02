package com.redditclone.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;
import com.redditclone.demo.model.Vote;
import com.redditclone.demo.model.VoteType;

@Repository
/**
 * VoteRepository provides database repository methods to find and access vote
 * details based on the post and user details.
 * 
 * @author Santhosh Kumar J
 *
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

	/**
	 * findTopByPostAndUserOrderByVoteIdDesc method finds and returns the top
	 * matching vote with respective to the post,vote type and the user from the
	 * votes which is sorted by descending order by vote id.
	 *
	 * @param post        the post for which vote needs to be found.
	 * @param voteType    the vote type for which vote needs to be found.
	 * @param currentUser the current user for which vote needs to be found.
	 * @return the optional vote for given inputs, if matching result is
	 *         found,returns vote data. if not, Optional.empty() is returned by
	 *         default.
	 */
	Optional<Vote> findTopByPostAndVoteTypeAndUserOrderByVoteIdDesc(Post post, VoteType voteType, User currentUser);

	/**
	 * findTopByPostAndUserOrderByVoteIdDesc method finds and returns the top
	 * matching vote with respective to the post,vote type and the user from the
	 * votes which is sorted by descending order by vote id.
	 *
	 * @param post        the post for which vote needs to be found.
	 * @param currentUser the current user for which vote needs to be found.
	 * @return the optional vote for given inputs, if matching result is
	 *         found,returns vote data. if not, Optional.empty() is returned by
	 *         default.
	 */
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
