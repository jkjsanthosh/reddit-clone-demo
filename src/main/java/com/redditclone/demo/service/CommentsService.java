package com.redditclone.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.dto.CommentDto;
import com.redditclone.demo.exceptions.PostNotFoundException;
import com.redditclone.demo.mapper.CommentMapper;
import com.redditclone.demo.model.Comment;
import com.redditclone.demo.model.NotificationEmailInfo;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;
import com.redditclone.demo.repository.CommentRepository;
import com.redditclone.demo.repository.PostRepository;
import com.redditclone.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {

	/** The comment mapper which used to map between comment model and dto. */
	private final CommentMapper commentMapper;

	/**
	 * The comment repository which is used to perform CRUD operations on data from
	 * the comment table in the database.
	 */
	private final CommentRepository commentRepository;

	/**
	 * The post repository which is used to find the post information from the post
	 * table in the database on which post comments are made.
	 */
	private final PostRepository postRepository;

	/** The auth service which will be used to get logged in user details */
	private AuthService authService;

	/** The mail content builder service which is used to build mail content. */
	private final MailContentBuilderService mailContentBuilderService;

	/** The mail sender service which is used to send mail notification. */
	private final MailSenderService mailSenderService;

	/**
	 * The user repository which is used to find the user information from the user
	 * table in the database by username.
	 */
	private final UserRepository userRepository;

	/**
	 * createAndSaveNewComment method create and save new comment information into
	 * comment table in the database.It returns the the created comment info if
	 * creation is successful. <br>
	 * 
	 * For creation of comment it finds and use related post information by it's
	 * name. Also it uses logged in user details as input.<br>
	 * 
	 * throws PostNotFoundException if related post information is not found.
	 * 
	 * @param newCommentCreationRequest the comment dto information which needs to
	 *                                  be created and saved.
	 * @return the comment dto after saved into the table.
	 */
	@Transactional
	public CommentDto createAndSaveNewComment(CommentDto newCommentCreationRequest) {
		Post relatedPost = postRepository.findById(newCommentCreationRequest.getPostId())
				.orElseThrow(() -> new PostNotFoundException(newCommentCreationRequest.getPostId().toString()));
		Comment newlyCreatedCommentModel = commentMapper.mapCommentModelFromDto(newCommentCreationRequest, relatedPost,
				authService.getCurrentLoggedInUser());
		sendCommentNotification(relatedPost, relatedPost.getUser());
		return commentMapper.mapCommentDtoFromModel(commentRepository.save(newlyCreatedCommentModel));
	}

	/**
	 * getCommentsByPostId method gets the matching comments information by post id
	 * and returns it .
	 *
	 * @param postId the id of post which related comments needs to be found.
	 * @return List<CommentDto> the matching list of comments information.
	 */
	@Transactional(readOnly = true)
	public List<CommentDto> getCommentsByPostId(Long postId) {
		Post relatedPost = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findAllByPost(relatedPost).stream().map(commentMapper::mapCommentDtoFromModel)
				.collect(Collectors.toList());
	}

	/**
	 * getCommentsByUsername method gets the matching comments information by
	 * username and returns it .
	 *
	 * @param username the username by whom the post comments needs to be found .
	 * @return List<CommentDto> the matching list of comment information.
	 */
	@Transactional(readOnly = true)
	public List<CommentDto> getCommentsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
		return commentRepository.findAllByUser(user).stream()
				.map(commentMapper::mapCommentDtoFromModel).collect(Collectors.toList());
	}

	private void sendCommentNotification(Post relatedPost, User notificationUser) {
		String notificationMessage = relatedPost.getUser().getUsername() + " commented on your post "
				+ relatedPost.getPostName();
		String notificationMailHtmlTemplate = mailContentBuilderService.buildHtmlMailTemplateText(notificationMessage);
		mailSenderService.sendMailNotification(NotificationEmailInfo.builder().mailSubject(notificationMessage)
				.messageBody(notificationMailHtmlTemplate).recipientEmailAddress(notificationUser.getEmail()).build());
	}
}
