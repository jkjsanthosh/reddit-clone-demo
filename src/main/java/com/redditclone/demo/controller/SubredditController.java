package com.redditclone.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.dto.SubredditDto;
import com.redditclone.demo.service.SubredditService;

import lombok.AllArgsConstructor;

/**
 * SubredditController class provides api request methods to handle all kind of
 * operations such as creation and fetch subreddits.
 * 
 */
@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

	/**
	 * The subreddit service which is used to perform all kind of operations on
	 * subreddits.
	 */
	private final SubredditService subredditService;

	/**
	 * createSubreddit method handles creation of new subreddit and returns the
	 * created subreddit.
	 *
	 * @param SubredditDto the subreddit dto which contains information to create
	 *                     new subreddit.
	 * @return esponseEntity<SubredditDto> the response entity which contains the
	 *         created subreddit as response.
	 */
	@PostMapping("createSubreddit")
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto SubredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.createandSaveSubreddit(SubredditDto));
	}

	/**
	 * getAllSubreddits method fetches and return all the subreddits.
	 *
	 * @return <List<SubredditDto>> the list of all subreddits
	 */
	@GetMapping("getAllSubreddits")
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.getAllSubreddits());
	}
}
