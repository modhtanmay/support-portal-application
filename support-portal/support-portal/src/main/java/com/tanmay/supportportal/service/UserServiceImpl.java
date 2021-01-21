package com.tanmay.supportportal.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tanmay.supportportal.domain.User;
import com.tanmay.supportportal.domain.UserPrincipal;
import com.tanmay.supportportal.enumeration.Roles;
import com.tanmay.supportportal.exception.domain.EmailExistException;
import com.tanmay.supportportal.exception.domain.UserNotFoundException;
import com.tanmay.supportportal.exception.domain.UsernameExistException;
import com.tanmay.supportportal.repository.UserRepository;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // will use this to find
																								// actual user
		// TODO Auto-generated method stub
		User user = userRepository.findUserByUserName(username);
		if (user == null) {
			LOGGER.error("User not found by username: " + username);
			throw new UsernameNotFoundException("User not found by username: " + username);
		} else {
			validateLoginAttempt(user);						// this will check whether account is active,loggedin etc functionalities...
			user.setLastLoginDate(new Date());
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			userRepository.save(user);
			UserPrincipal principal = new UserPrincipal(user);
			LOGGER.info("returning found user by username: " + username);
			return principal;
		}
	}

	private void validateLoginAttempt(User user) {
		// TODO Auto-generated method stub
		if(user.isNotLocked()) {
			if(loginAttemptService.exceededMaxAttempt(user.getUserName())) {	// if exceeded attempts to login
				user.setNotLocked(false);										// set account to Locked
			} else {
				user.setNotLocked(true);										// else dont lock account
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUserName());		// if account locked remove user from cache
		}
		
	}

	@Override
	public User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {
		// TODO Auto-generated method stub
		validateNewUserNameandEmail(StringUtils.EMPTY,userName,email);
		User user = new User();
		user.setUserId(generateUserId());
		String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRoles(Roles.ROLE_USER.name());
		user.setAuthorities(Roles.ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl());
		userRepository.save(user);
		LOGGER.info("New User password : "+password);
		return user;
	}

	private String getTemporaryProfileImageUrl() {
		// TODO Auto-generated method stub
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString(); // ServletUriComponentBuilder adds prefix on whatever platform it is i.e. http://localhost:8080/, or if google then www.google.com/ etc
	}

	private String encodePassword(String password) {
		// TODO Auto-generated method stub
		return bCryptPasswordEncoder.encode(password);
	}

	private String generatePassword() {
		// TODO Auto-generated method stub
		return RandomStringUtils.randomAlphanumeric(10);  // random String of 10 alphanumeric digits
	}

	private String generateUserId() {
		// TODO Auto-generated method stub
		return RandomStringUtils.randomNumeric(10);  // random String of 10 numeric digits
	}

	private User validateNewUserNameandEmail(String currentUserName,String newUserName, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
		// TODO Auto-generated method stub
		User userByNewUserName = findUserByUserName(newUserName);
		User userByNewEmail = findUserByEmail(newEmail);

		if(StringUtils.isNotBlank(currentUserName)) {
			User currentUser = findUserByUserName(currentUserName);
			if(currentUser == null) {
				throw new UserNotFoundException("No user found by username "+ currentUserName);
			}
			if(userByNewUserName != null && !currentUser.getId().equals(userByNewUserName.getId())) {
				throw new UsernameExistException("UserName already exists");
			}
			if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException("Email already exists");
			}	
			return currentUser;
		} else {
			if(userByNewUserName != null) {
				throw new UsernameExistException("UserName already exists");
			}
			if(userByNewEmail != null) {
				throw new EmailExistException("Email already exists");
			}
			return null;
		}
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findUserByEmail(email);
	}
}
