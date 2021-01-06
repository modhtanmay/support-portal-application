package com.tanmay.supportportal.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.supportportal.domain.User;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	@GetMapping("/home")
	public String showUser() {
		return "app works";
	}

}
