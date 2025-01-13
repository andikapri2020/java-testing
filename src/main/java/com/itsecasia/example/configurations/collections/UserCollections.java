package com.itsecasia.example.configurations.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.itsecasia.example.models.User;

public class UserCollections {

	private ConcurrentMap<String, User> concurrentMap;

	public UserCollections() {
		this.concurrentMap = new ConcurrentHashMap<>();
	}

	public ConcurrentMap<String, User> getConcurrentMap() {
		return this.concurrentMap;
	}

}
