package com.itsecasia.example.security.jwt;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.itsecasia.example.configurations.collections.UserCollections;
import com.itsecasia.example.models.User;
import com.itsecasia.example.security.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

	private JwtUtil jwtUtil;

	private ConcurrentMap<String, User> users;

	public AuthTokenFilter(JwtUtil jwtUtil, UserCollections userCollections) {
		this.jwtUtil = jwtUtil;
		this.users = userCollections.getConcurrentMap();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
				Claims claims1 = jwtUtil.getClaimsFromJwtToken(jwt);

				User user = new User();

				user.setEmail(Optional.ofNullable(claims1.get("email")).orElse("").toString());
				user.setId(claims1.getSubject());
				user.setRole(Optional.ofNullable(claims1.get("role1")).orElse("none").toString());
				user.setStatus("");
				user.setUsername(Optional.ofNullable(claims1.get("name")).orElse("").toString());

				if (users.containsKey(user.getId())) {
					users.remove(user.getId());
				}
				users.put(user.getId(), user);

				UserDetails userDetails = UserDetailsImpl.build(user);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			LOGGER.error("Cannot set user authentication: {}", e.getMessage(), e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
