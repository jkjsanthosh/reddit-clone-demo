package com.redditclone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
	 * findByUser method finds and returns the list of posts for given user.
	 * 
	 * @param the input user details which needs to be found.
	 * @return the list of post details for given user.
	 */
	List<Post> findByUser(User searchInputuser);

	/**
	 * findAllBySubreddit methods finds and returns the all the posts related to for
	 * given subreddit.
	 * 
	 * @param the input subreddit details which needs to be found.
	 * @return the list of post details related to the subreddit.
	 */
	List<Post> findAllBySubreddit(Subreddit searchInputSubreddit);

}