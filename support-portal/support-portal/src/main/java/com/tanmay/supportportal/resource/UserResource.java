package com.tanmay.supportportal.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.supportportal.constant.SecurityConstant;
import com.tanmay.supportportal.domain.User;
import com.tanmay.supportportal.domain.UserPrincipal;
import com.tanmay.supportportal.exception.domain.EmailExistException;
import com.tanmay.supportportal.exception.domain.ExceptionHandling;
import com.tanmay.supportportal.exception.domain.UserNotFoundException;
import com.tanmay.supportportal.exception.domain.UsernameExistException;
import com.tanmay.supportportal.service.UserService;
import com.tanmay.supportportal.utility.JWTTokenProvider;

@RestController
@RequestMapping(value = { "/", "/user" })
public class UserResource extends ExceptionHandling {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@GetMapping("/home")
	public String showUser() throws EmailExistException {
//		return "app works";
		throw new EmailExistException("This email address is already taken");    // only for debugging purpose
	}
	
	@PostMapping("/login")
	public ResponseEntity<User>loginUser(@RequestBody User user) {
		authenticate(user.getUserName(),user.getPassword());      // if success then only goes below else throws exception
		User loginUser = userService.findUserByUserName(user.getUserName());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);   // after userPrincipal object HttpHeaders can be created. getJwtHeader will returen required headers.
		return new ResponseEntity<>(loginUser,jwtHeader,HttpStatus.OK);
	}
	
	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}

	private void authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		// For authentication we need to bring AuthenticationManager and it will authenticate the user
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
		
	}

	@PostMapping("/register")
	public ResponseEntity<User>registerUser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
		User newUser = userService.register(user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmail());
		return new ResponseEntity<>(newUser,HttpStatus.OK);
	}

}
