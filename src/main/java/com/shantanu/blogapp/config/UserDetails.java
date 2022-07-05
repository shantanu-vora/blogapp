package com.shantanu.blogapp.config;

import com.shantanu.blogapp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

	private String username;
	private String email;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email =  user.getEmail();
		this.authorities = Arrays.stream(user.getRole().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
