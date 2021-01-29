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

import com.redditclone.demo.dto.CommentDto;
import com.redditclone.demo.service.CommentsService;

import lombok.AllArgsConstructor;

/**
 * CommentsController class provides api request methods to handle all kind of
 * operations on comments such as creation of comment and fetching the comments
 * information based on different input attributes which is linked to comment.
 */
@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

	/**
	 * The comments service which will be used to perform all kind of operations on
	 * comments.
	 */
	private final CommentsService commentsService;

	/**
	 * createComment method handles api request for creation of new comment and
	 * returns the created comment.
	 *
	 * @param SubredditDto the comment dto which contains information to create new
	 *                     comment.
	 * @return ResponseEntity<CommentDto> the response entity which contains the
	 *         created comment as response.
	 */
	@PostMapping("create")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto newCommentCreationRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(commentsService.createAndSaveNewComment(newCommentCreationRequest));
	}

	/**
	 * getAllCommentsByPost method handles the api request to find the matching
	 * comments information by post id and returns it .
	 *
	 * @param postId the id of post which related comments needs to be found.
	 * @return ResponseEntity<List<CommentDto>> the response entity which contains
	 *         the matching list of comments information as response.
	 */
	@GetMapping("/by-post/{id}")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getCommentsByPostId(id));
	}

	/**
	 * getCommentsByUsername method handles the api request to find the matching
	 * comments information by username and returns it .
	 *
	 * @param username the username by whom the post comments needs to be found .
	 * @return ResponseEntity<List<CommentDto>> the response entity which contains
	 *         the matching list of comment information as response.
	 */
	@GetMapping("/by-username/{username}")
	public ResponseEntity<List<CommentDto>> getCommentsByUsername(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getCommentsByUsername(username));
	}
}
