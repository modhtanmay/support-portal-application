package com.tanmay.supportportal.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.supportportal.domain.User;
import com.tanmay.supportportal.exception.domain.ExceptionHandling;

@RestController
@RequestMapping(value = "/user")
public class UserResource extends ExceptionHandling{
	
	@GetMapping("/home")
	public String showUser() {
		return "app works";
	}

}
