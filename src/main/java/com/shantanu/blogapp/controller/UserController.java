package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
	private UserDetailsServiceImpl userDetailService;

	@PostMapping("/registerUser")
	public String saveUserData(User user) {
		Boolean isUserRegistered = userDetailService.saveUserDetails(user);
		if(!isUserRegistered) {
			return "redirect:/signup?error";
		} else {
			return "redirect:/login";
		}
	}
}
