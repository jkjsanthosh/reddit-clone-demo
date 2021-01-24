package com.redditclone.demo.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.redditclone.demo.exceptions.SpringRedditException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class JwtProvider.
 */
@Service
public class JwtProvider {

	/** The key store. */
	private KeyStore keyStore;

	public String generateToken(Authentication authentication) {
		return Jwts.builder().setSubject(authentication.getName())
				.signWith(SignatureAlgorithm.RS256, getPrivateSignKey()).compact();
	}

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/reddit-secret-key-store.jks");
			keyStore.load(resourceAsStream, "reddit@secret$key%store".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException exception) {
			throw new SpringRedditException("Exception occured while loading keystore", exception);
		}
	}

	private PrivateKey getPrivateSignKey() {
		try {
			return (PrivateKey) keyStore.getKey("reddit-secret-key", "reddit%secret$key".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException exception) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore", exception);
		}
	}
}
