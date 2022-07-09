package com.shantanu.blogapp.restcontroller;

import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.exception.CustomException;
import com.shantanu.blogapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestUserController {

	@Autowired
	private UserDetailsServiceImpl userDetailService;

	@PostMapping("/users")
	public String saveUserData(@RequestBody User user) {
		Boolean isUserRegistered = userDetailService.saveUserDetails(user);
		if(!isUserRegistered) {
			throw new CustomException("Username or password already exists");
		} else {
			return "User registered";
		}
	}
}