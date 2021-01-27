package com.redditclone.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.dto.SubredditDto;
import com.redditclone.demo.service.SubredditService;

import lombok.AllArgsConstructor;

/**
 * SubredditController class provides api request methods to handle all kind of
 * operations on subreddits such as creation of subreddit and fetching the
 * subreddits information based on different input attributes which is linked to
 * subreddit.
 */
@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

	/**
	 * The subreddit service which will be used to perform all kind of operations on
	 * subreddits.
	 */
	private final SubredditService subredditService;

	/**
	 * createSubreddit method handles api request for creation of new subreddit and
	 * returns the created subreddit.
	 *
	 * @param SubredditDto the subreddit dto which contains information to create
	 *                     new subreddit.
	 * @return ResponseEntity<SubredditDto> the response entity which contains the
	 *         created subreddit as response.
	 */
	@PostMapping("create")
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto SubredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.createandSaveSubreddit(SubredditDto));
	}

	/**
	 * getSubreddit method handles the api request to find the matching subreddit
	 * information by id and returns it .
	 *
	 * @param id the id which related subreddit needs to be found.
	 * @return ResponseEntity<SubredditDto> the response entity which contains the
	 *         matching subreddit information as response.
	 */
	@GetMapping("get/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
	}

	/**
	 * getAllSubreddits method handles the api request to get and return all the
	 * subreddits.
	 *
	 * @return ResponseEntity<List<SubredditDto>> the response entity which contains
	 *         the list of all subreddit information as response.
	 */
	@GetMapping("getAll")
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.getAllSubreddits());
	}


}
