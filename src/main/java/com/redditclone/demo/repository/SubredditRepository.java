package com.redditclone.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redditclone.demo.model.Subreddit;

/**
 * SubredditRepository provides database repository methods to find and access
 * subreddit details based on the subreddit name.
 * 
 * @author Santhosh Kumar J
 *
 */
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

	/**
	 * findByName method finds and returns the resulting subreddit details for given
	 * subreddit name.
	 * 
	 * @param the input subreddit name which needs to be found.
	 * @return the subreddit details for given subreddit name.
	 */
	Optional<Subreddit> findByName(String inputSubredditName);
}
