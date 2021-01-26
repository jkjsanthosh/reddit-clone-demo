package com.redditclone.demo.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.redditclone.demo.exceptions.RedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtProvider class provides methods to generate json web token which will be
 * used for authentication of user login.
 */
@Service
public class JwtProvider {

	/**
	 * The key store which contains java key store details which will be used to
	 * generate token.
	 */
	private KeyStore keyStore;

	public String generateToken(Authentication authentication) {
		return Jwts.builder().setSubject(authentication.getName())
				.signWith(SignatureAlgorithm.RS256, getPrivateSignKey()).compact();
	}

	/**
	 * initKeyStoreInstance method initializes and load the java key store instance
	 * from java key store resource file.
	 */
	@PostConstruct
	public void initKeyStoreInstance() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/reddit-secret-key-store.jks");
			keyStore.load(resourceAsStream, "reddit@secret$key%store".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException exception) {
			throw new RedditException("Exception occured while loading keystore", exception);
		}
	}

	/**
	 * getPrivateSignKey method gets and return the private sign key from java key
	 * store instance.
	 *
	 * @return the private sign key which will be used for generation of json web
	 *         token.
	 */
	private PrivateKey getPrivateSignKey() {
		try {
			return (PrivateKey) keyStore.getKey("reddit-secret-key", "reddit%secret$key".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException exception) {
			throw new RedditException("Exception occured while retrieving private key from keystore", exception);
		}
	}

	/**
	 * validateTokenAndGetJwsClaims method validate jwt token from the request and
	 * parse and return the jws claims with jwts parser using public key.
	 *
	 * @param jwtFroṁRequest the jwt froṁ request
	 * @return the claims
	 */
	public Claims validateTokenAndGetJwsClaims(String jwtFroṁRequest) {
		return Jwts.parser().setSigningKey(getPublicSignKey()).parseClaimsJws(jwtFroṁRequest).getBody();
	}

	/**
	 * getPublicSignKey method gets and return the public sign key from java key
	 * store instance's certificate.
	 *
	 * @return the public which will be used for validation of json web token.
	 */
	private PublicKey getPublicSignKey() {
		try {
			return keyStore.getCertificate("reddit-secret-key").getPublicKey();
		} catch (KeyStoreException exception) {
			throw new RedditException("Exception occured while retrieving public key from keystore", exception);
		}
	}
}
