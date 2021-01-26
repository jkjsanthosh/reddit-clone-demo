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

import com.redditclone.demo.dto.PostDto;
import com.redditclone.demo.service.PostService;

import lombok.AllArgsConstructor;

/**
 * PostController class provides api request methods to handle all kind of
 * operations such as creation of post and fetching the post information based
 * on different input attributes which is linked to post.
 */
@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

	/**
	 * The post service which will be used to perform all kind of operations on
	 * posts.
	 */
	private final PostService postService;

	/**
	 * createPost method handles api request for creation of new post and returns
	 * the created post.
	 *
	 * @param SubredditDto the post dto which contains information to create new
	 *                     post.
	 * @return ResponseEntity<PostDto> the response entity which contains the
	 *         created post as response.
	 */
	@PostMapping("createPost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto newPostCreationRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createAndSaveNewPost(newPostCreationRequest));
	}

	/**
	 * getPost method handles the api request to find the matching post information
	 * by id and returns it .
	 *
	 * @param id the id which related post needs to be found.
	 * @return ResponseEntity<PostDto> the response entity which contains the
	 *         matching post information as response.
	 */
	@GetMapping("/getPost/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}

	/**
	 * getAllPosts method handles the api request to get and return all the posts.
	 *
	 * @return ResponseEntity<List<PostDto>> the response entity which contains the
	 *         list of all post information as response.
	 */
	@GetMapping("/getAllPosts")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}

	/**
	 * getPostsBySubreddit method handles the api request to find the matching posts
	 * information by related subreddit id and returns it .
	 *
	 * @param id the id of subreddit which related posts needs to be found.
	 * @return ResponseEntity<List<PostDto>> the response entity which contains the
	 *         matching list of post information as response.
	 */
	@GetMapping("/getPostsBySubreddit/{id}")
	public ResponseEntity<List<PostDto>> getPostsBySubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}

	/**
	 * getPostsByUserName method handles the api request to find the matching posts
	 * information by username and returns it .
	 *
	 * @param username the username of which related posts needs to be found.
	 * @return ResponseEntity<List<PostDto>> the response entity which contains the
	 *         matching list of post information as response.
	 */
	@GetMapping("/getPostsByUserName/{username}")
	public ResponseEntity<List<PostDto>> getPostsByUserName(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUserName(username));
	}

}
