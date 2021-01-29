package com.redditclone.demo.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.exceptions.RedditException;
import com.redditclone.demo.model.RefreshToken;
import com.redditclone.demo.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

/**
 * RefreshTokenService which provides service methods to perform all kind of
 * operations on refresh token such as creation,fetch and validate and delete of
 * refresh tokens.
 */
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	/**
	 * The refresh token repository which is used to perform CRUD operations on data
	 * from the refresh token table in the database..
	 */
	private RefreshTokenRepository refreshTokenRepository;

	/**
	 * generateAndSaveRefreshToken method generates random refresh token by
	 * generating random uuid and returns refresh token after saving into database.
	 *
	 * @return the refresh token information after save operation.
	 */
	public RefreshToken generateAndSaveRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDateTime(Instant.now());
		return refreshTokenRepository.save(refreshToken);
	}

	/**
	 * validateRefreshToke method fetches the refresh token information to validate
	 * refresh token using refresh token from the database.
	 *
	 * throws RedditException with invalid refresh token message if refresh token
	 * information is not found.
	 *
	 * @param refreshToken the refresh token to validate.
	 */
	@Transactional(readOnly = true)
	public void validateRefreshToken(String refreshToken) {
		refreshTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new RedditException("Invalid refresh token"));
	}

	/**
	 * deleteRefreshToken method deletes the refresh token information using refresh
	 * token from the database.
	 *
	 * @param refreshToken the refresh token
	 */
	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteByToken(refreshToken);
	}
}
