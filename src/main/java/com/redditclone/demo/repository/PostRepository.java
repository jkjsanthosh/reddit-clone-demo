package com.redditclone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.Subreddit;
import com.redditclone.demo.model.User;

@Repository
/**
 * PostRepository provides database repository methods to find and access post
 * details based on the subreddit and user details.
 * 
 * @author Santhosh Kumar J
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * findAllByUser method finds and returns the list of posts for given user.
	 * 
	 * @param the input user details which needs to be found.
	 * @return the list of post details for given user.
	 */
	List<Post> findAllByUser(User searchInputuser);

	/**
	 * findAllBySubreddit methods finds and returns the all the posts related to for
	 * given subreddit.
	 * 
	 * @param the input subreddit details which needs to be found.
	 * @return the list of post details related to the subreddit.
	 */
	List<Post> findAllByRelatedSubreddit(Subreddit searchInputSubreddit);

	/**
	 * updateVoteCount method updates vote count to given updated vote count.
	 *
	 * @param postId           the post id of post which needs to be updated.
	 * @param updatedVoteCount the updated vote count which needs to be updated.
	 * @return the int the update status.
	 */
	@Modifying
	@Transactional
	@Query("update Post set vote_count = :updatedVoteCount where post_id = :postId")
	int updateVoteCount(Long postId, Integer updatedVoteCount);

}