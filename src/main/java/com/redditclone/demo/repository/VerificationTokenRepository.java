package com.redditclone.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redditclone.demo.model.RefreshToken;
import com.redditclone.demo.model.VerificationToken;

@Repository
/**
 * VerificationTokenRepository provides database repository methods to find and
 * access authentication token details based on the input verification token .
 * 
 * @author Santhosh Kumar J
 *
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	/**
	 * findByToken method finds and returns the matching refresh token for given
	 * input token.
	 * 
	 * @param the input token details which needs to be found.
	 * @return the optional refresh token for given input token, Holds the refresh
	 *         token data if matching refresh token is found, if not,
	 *         Optional.empty() is returned by default.
	 */
	Optional<RefreshToken> findByToken(String searchInputToken);
}
