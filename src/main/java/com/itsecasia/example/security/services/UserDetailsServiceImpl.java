package com.itsecasia.example.security.services;

import java.util.concurrent.ConcurrentMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itsecasia.example.configurations.collections.UserCollections;
import com.itsecasia.example.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ConcurrentMap<String, User> users;

	public UserDetailsServiceImpl(UserCollections userCollections) {
		this.users = userCollections.getConcurrentMap();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = users.get(username);

		return UserDetailsImpl.build(user);
	}

}
