package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/registerUser")
	public String saveUserData(User user) {

		System.out.println("Username: " + user.getUsername() +
											 " Password: " + user.getPassword() +
											 " Email: " + user.getEmail());

		userService.saveUserDetails(user);

		return "redirect:/login";
	}
}
