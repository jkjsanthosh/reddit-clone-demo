package com.redditclone.demo.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.redditclone.demo.constants.AuthEndPointConstants;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

/**
 * JwtAuthenticationFilter is http authentication filter class which
 * authenticate and filters each and every http request api request with valid
 * jwt. It skips the authentication for login,signup,logout, refresh token and
 * verification user account api request mappings.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * The jwt provider which is used to validate jwt to authentication user
	 * account.
	 */
	private final JwtProvider jwtProvider;

	/**
	 * The Constant REQUEST_MAPPING_PATHS_TO_SKIP_AUTHENTICATION which contains
	 * request mapping paths login,signup,logout, refresh token and verification
	 * entry points which will be skipped from authentication.
	 */
	private static final List<String> REQUEST_MAPPING_PATHS_TO_SKIP_AUTHENTICATION = Collections.unmodifiableList(Arrays
			.asList(AuthEndPointConstants.AUTH_REQUEST_MAPPING + AuthEndPointConstants.SIGNUP_REQUEST_MAPPING_METHOD,
					AuthEndPointConstants.AUTH_REQUEST_MAPPING + AuthEndPointConstants.LOGIN_REQUEST_MAPPING_METHOD,
					AuthEndPointConstants.AUTH_REQUEST_MAPPING
							+ AuthEndPointConstants.REFRESH_TOKEN_REQUEST_MAPPING_METHOD,
					AuthEndPointConstants.AUTH_REQUEST_MAPPING + AuthEndPointConstants.lOG_OUT_REQUEST_MAPPING_METHOD,
					"/v2/api-docs", "/configuration/ui", "/swagger-ui.html"));

	/**
	 * The user details service which is used to fetch user details and prepare
	 * authentication information.
	 */
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getJwtFroṁRequest(request);
		Claims jwsClaims = jwtProvider.validateTokenAndGetJwsClaims(jwt);
		if (StringUtils.hasText(jwt) && Objects.nonNull(jwsClaims)) {
			String username = jwsClaims.getSubject();
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String currentRequestURI = request.getRequestURI();
		List<String> allowedGetApiMethodList = Arrays.asList("/api/subreddit/", "/api/posts/", "/api/comments/");
		boolean filterAndAllowGetApiMethods = request.getMethod().equals(HttpMethod.GET.name())
				&& allowedGetApiMethodList.stream().filter(getApiMethod -> currentRequestURI.contains(getApiMethod))
						.findFirst().isPresent();
		boolean filterAndAllowSwaggerApiUrls = Arrays
				.asList("/v2/api-docs", "/configuration/ui", "/configuration/security", "/swagger-ui.html")
				.contains(currentRequestURI);
		boolean allowedAuthApiUrls = REQUEST_MAPPING_PATHS_TO_SKIP_AUTHENTICATION.contains(currentRequestURI)
		|| currentRequestURI.contains(AuthEndPointConstants.AUTH_REQUEST_MAPPING
						+ AuthEndPointConstants.VERIFY_NEW_ACCOUNT_REQUEST_MAPPING_METHOD);
		return filterAndAllowGetApiMethods || filterAndAllowSwaggerApiUrls || allowedAuthApiUrls;
	}

	/**
	 * getJwtFroṁRequest method parses and returns jwt from the bearer token.
	 *
	 * @param request the http request which contains authorization headers.
	 * @return the jwt
	 */
	private String getJwtFroṁRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return bearerToken;

	}

}
