package com.redditclone.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.dto.PostDto;
import com.redditclone.demo.exceptions.PostNotFoundException;
import com.redditclone.demo.exceptions.SubredditNotFoundException;
import com.redditclone.demo.mapper.PostMapper;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.Subreddit;
import com.redditclone.demo.model.User;
import com.redditclone.demo.repository.PostRepository;
import com.redditclone.demo.repository.SubredditRepository;
import com.redditclone.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * PostService which provides service methods to perform all kind of operations
 * on posts such as creation of post and fetching the post information based on
 * different input attributes which is linked to post.
 */
@Service
@AllArgsConstructor
public class PostService {

	/** The post mapper which used to map between post model and dto. */
	private final PostMapper postMapper;

	/**
	 * The post repository which is used to perform CRUD operations on data from the
	 * post table in the database.
	 */
	private final PostRepository postRepository;

	/** The auth service which will be used to get logged in user details */
	private AuthService authService;

	/**
	 * The subreddit repository which is used to find the subreddit information from
	 * the subreddit table in the database for which post is related to.
	 */
	private final SubredditRepository subredditRepository;

	/**
	 * The user repository which is used to find the user information from the user
	 * table in the database by username.
	 */
	private final UserRepository userRepository;


	/**
	 * createAndSaveNewPost method create and save new post information into post
	 * table in the database.It returns the the created post info if creation is
	 * successful. <br>
	 * 
	 * For creation of post it finds and use related subreddit information by it's
	 * name. Also it uses logged in user details as input.<br>
	 * 
	 * throws SubredditNotFoundException if related subreddit information is not
	 * found.
	 * 
	 * @param newPostCreationRequest the post dto information which needs to be
	 *                               created and saved.
	 * @return the post dto after saved into the table.
	 */
	@Transactional
	public PostDto createAndSaveNewPost(PostDto newPostCreationRequest) {
		Subreddit relatedSubreddit = subredditRepository.findByName(newPostCreationRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(newPostCreationRequest.getSubredditName()));
		Post newlyCreatedPostModel = postMapper.mapPostModelFromDto(newPostCreationRequest, relatedSubreddit,
				authService.getCurrentLoggedInUser());
		return postMapper.mapPostDtoFromModel(postRepository.save(newlyCreatedPostModel));
	}

	/**
	 * getPost method get and returns the post by id.
	 *
	 * throws PostNotFoundException if matching post is not found.
	 * 
	 * @param id the id which related post needs to be found.
	 * @return the post which is related to id.
	 */
	@Transactional(readOnly = true)
	public PostDto getPost(Long id) {
		Post relatedPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapPostDtoFromModel(relatedPost);
	}

	/**
	 * getAllPosts method fetches and returns all posts.
	 *
	 * @return List<PostDto> the all posts.
	 */
	@Transactional(readOnly = true)
	public List<PostDto> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapPostDtoFromModel).collect(Collectors.toList());
	}

	/**
	 * getPostsBySubredditId method gets the matching posts information by related
	 * subreddit id and returns it .
	 *
	 * throws SubredditNotFoundException if related subreddit information is not
	 * found.
	 * 
	 * @param subredditId the id of subreddit which related posts needs to be found.
	 * @return List<PostDto> the list of matching post dto's.
	 */
	@Transactional(readOnly = true)
	public List<PostDto> getPostsBySubredditId(Long subredditId) {
		Subreddit relatedSubreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		return postRepository.findAllByRelatedSubreddit(relatedSubreddit).stream().map(postMapper::mapPostDtoFromModel)
				.collect(Collectors.toList());
	}

	/**
	 * getPostsByUserName method get the matching posts information by username and
	 * returns it .
	 *
	 * @param username the username of which related posts needs to be found.
	 * @return List<PostDto> the list of matching post dto's.
	 */
	@Transactional(readOnly = true)
	public List<PostDto> getPostsByUserName(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
		return postRepository.findAllByUser(user).stream()
				.map(postMapper::mapPostDtoFromModel).collect(Collectors.toList());
	}

}
