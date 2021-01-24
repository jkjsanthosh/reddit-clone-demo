package com.redditclone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redditclone.demo.model.Comment;
import com.redditclone.demo.model.Post;
import com.redditclone.demo.model.User;

@Repository
/**
 * CommentRepository provides database repository methods to find and access
 * comment details based on the post and user details.
 * 
 * @author Santhosh Kumar J
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

	/**
	 * findByPost method finds and returns the list of comments has been made for
	 * given post.
	 * 
	 * @param the input post details which needs to be found.
	 * @return the list of comments details for matching post.
	 */
	List<Comment> findByPost(Post searchInputpost);

	/**
	 * findAllByUser methods finds and returns the all the posts for given user.
	 * 
	 * @param the input user details which needs to be found.
	 * @return the list of comments details related to the user.
	 */
	List<Comment> findAllByUser(User searchInputuser);
}
