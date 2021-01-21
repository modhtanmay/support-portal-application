package com.tanmay.supportportal.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

	private static final int MAXIMUM_NUMBER_OF_ATTEMPT = 5;
	private static final int ATTEMPT_INCREMENT = 1;
	private LoadingCache<String, Integer> loginAttemptCache; // here we are determining key(user) and the value(attempt)

	public LoginAttemptService() {		// initializing the cache
		super();
		loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

//	------- CACHE -------
//	USER			 ATTEMPTS
//	user1				1 + 1 + 1 everytime loggedin try 
//	user2				1
//	user3				2

	public void evictUserFromLoginAttemptCache(String username) { // here we will remove user from the cache
		loginAttemptCache.invalidate(username); // finds the key and removes it
	}

	public void addUserToLoginAttemptCache(String username) { // adds user to the cache
		int attempts = 0; // increment the value and put them in cache
		try {
			attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);			// finds values of get(username) and adds
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		loginAttemptCache.put(username, attempts);
	}

	public boolean exceededMaxAttempt(String username) { // checks max attempts exceeded or
																					// not
		try {
			return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPT;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
