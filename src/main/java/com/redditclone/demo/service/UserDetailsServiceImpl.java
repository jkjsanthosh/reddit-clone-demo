package com.redditclone.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.model.User;
import com.redditclone.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * The user repository which is used to fetch user details from the user table
	 * in the database.
	 */
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);
		org.springframework.security.core.userdetails.User userDetails = null;
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.isUserEnabled(), true, true, true, getAuthorities("USER"));
		} else {
			throw new UsernameNotFoundException("No User Details " + "Found with username : " + username);
		}
		return userDetails;
	}

	/**
	 * getAuthorities methods gets the list of authorities enabled or allowed for
	 * this role of the user.
	 *
	 * @param role the role of the user which authorities needs to be returned.
	 * @return the authorities allowed for the role of the user.
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

}
