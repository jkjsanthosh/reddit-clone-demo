package com.redditclone.demo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redditclone.demo.model.User;

@Repository
/**
 * UserRepository provides database repository methods to find and access user
 * details based on the user name.
 * 
 * @author Santhosh Kumar J
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * findByUsername method finds and returns the resulting user details for given
	 * user name.
	 * 
	 * @param the input user name which needs to be found.
	 * @return the user details for given user name.
	 */
	Optional<User> findByUserName(String username);

	/**
	 * updateUserAccountStatusByUserId method updates user account enabled status to
	 * given status by user id.
	 *
	 * @param userId        the user id needs to be updated.
	 * @param isUserEnabled the is user enabled status.
	 * @return the int the update status.
	 */
	@Modifying
	@Transactional
	@Query("update User set is_user_enabled = :isUserEnabled where user_id = :userId")
	int updateUserAccountStatusByUserId(Long userId, boolean isUserEnabled);
}
