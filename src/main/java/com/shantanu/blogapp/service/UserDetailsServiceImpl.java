package com.shantanu.blogapp.service;

import com.shantanu.blogapp.security.config.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//	@Autowired
//	private UserRepository userRepository;

//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<User> user = userRepository.findByEmail(email);
//
//		if(user.isEmpty()) {
//			throw new UsernameNotFoundException("Email " + email + " not found");
//		} else {
//			return new UserDetailsImpl(user.get());
//		}
//	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return new UserDetailsImpl(email);
	}
}
