package com.tanmay.supportportal.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.supportportal.domain.User;
import com.tanmay.supportportal.exception.domain.EmailExistException;
import com.tanmay.supportportal.exception.domain.ExceptionHandling;
import com.tanmay.supportportal.exception.domain.UserNotFoundException;
import com.tanmay.supportportal.exception.domain.UsernameExistException;
import com.tanmay.supportportal.service.UserService;

@RestController
@RequestMapping(value = { "/", "/user" })
public class UserResource extends ExceptionHandling {

	@Autowired
	UserService userService;
	
	@GetMapping("/home")
	public String showUser() throws EmailExistException {
//		return "app works";
		throw new EmailExistException("This email address is already taken");    // only for debugging purpose
	}
	
	@PostMapping("/register")
	public ResponseEntity<User>registeruser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
		User newUser = userService.register(user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmail());
		return new ResponseEntity<>(newUser,HttpStatus.OK);
	}

}
