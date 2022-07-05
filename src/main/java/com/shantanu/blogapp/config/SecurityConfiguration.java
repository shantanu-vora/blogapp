package com.shantanu.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.security.SecureRandom;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
						.antMatchers( "/post/newPost").authenticated()
						.antMatchers("/post/edit/*").authenticated()
						.antMatchers("/post/drafts/**").authenticated()
//						.antMatchers("/post/**").authenticated()
						.antMatchers(HttpMethod.POST, "/post/delete/{id}").authenticated()
//						.antMatchers(HttpMethod.POST, "/post/{postId}/updateComment/{commentId}").hasAnyRole("AUTHOR", "ADMIN")
//						.antMatchers("/", "/login", "/page/**", "/search/*","/post/{id}").permitAll()
						.antMatchers("/**").permitAll()
						.and().exceptionHandling().accessDeniedPage("/accessDenied")
						.and()
						.formLogin().loginPage("/login").defaultSuccessUrl("/")
						.and().logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
}
