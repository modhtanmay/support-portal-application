package com.tanmay.supportportal.service;

import java.util.List;

import com.tanmay.supportportal.domain.User;

public interface UserService {

	User register(String firstName, String lastName, String userName, String email);
	
	List<User> getUsers();
	
	User findUserByUserName(String userName);

	User findUserByEmail(String email);
}
