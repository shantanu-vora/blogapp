package com.shantanu.blogapp.service;

import com.shantanu.blogapp.config.UserDetailsImpl;
import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		User user;

		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			throw new UsernameNotFoundException("Not Found: " + username);
		}
		return new UserDetailsImpl(user);
	}

}
