package com.redditclone.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redditclone.demo.model.User;

@Repository
/**
 * UserRepository provides database repository methods to find and access
 * user details based on the user name.
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
	Optional<User> findByUsername(String username);
}
