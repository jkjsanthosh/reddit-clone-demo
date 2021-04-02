package com.redditclone.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redditclone.demo.dto.VoteDto;
import com.redditclone.demo.service.VoteService;

import lombok.AllArgsConstructor;

/**
 * VoteController class provides api request methods to handle all kind of
 * operations on votes such as creation of comment and fetching the votes
 * information based on different input attributes which is linked to vote.
 */
@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	/**
	 * The vote service which will be used to perform all kind of operations on
	 * vote.
	 */
	private final VoteService voteService;

	/**
	 * registerVote method handles api request to register a vote.
	 * 
	 * @param voteRegisterRequest the vote register request the vote register
	 *                            request which contains details to vote.
	 * @return the response entity with http status ok.
	 */
	@PostMapping("register")
	public ResponseEntity<Void> registerVote(@RequestBody VoteDto voteRegisterRequest) {
		voteService.registerVote(voteRegisterRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
