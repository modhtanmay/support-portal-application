package com.tanmay.supportportal.service;

import java.util.List;

import com.tanmay.supportportal.domain.User;
import com.tanmay.supportportal.exception.domain.EmailExistException;
import com.tanmay.supportportal.exception.domain.UserNotFoundException;
import com.tanmay.supportportal.exception.domain.UsernameExistException;

public interface UserService {

	User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<User> getUsers();
	
	User findUserByUserName(String userName);

	User findUserByEmail(String email);
}
