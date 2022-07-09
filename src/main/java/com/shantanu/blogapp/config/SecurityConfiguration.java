package com.shantanu.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.SecureRandom;

@EnableWebMvc
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
						.authorizeRequests()
						.antMatchers(HttpMethod.POST, "/api/posts/*").authenticated()
						.antMatchers(HttpMethod.PUT, "/api/posts/{id}", "/api/posts/{id}/comments/{commentId}").authenticated()
						.antMatchers(HttpMethod.DELETE, "/api/posts/{id}", "/api/posts/{postId}/comments/{commentId}").authenticated()
						.antMatchers("/post/newPost", "/post/edit/*", "/post/drafts/**").authenticated()
						.antMatchers("/v3/api-docs","/v2/api-docs","/swagger-resources/","/swagger-ui/","/webjars/","/api/v1/auth/"). permitAll()qqq
						.antMatchers("/**").permitAll()
						.anyRequest().authenticated()
						.and()
						.httpBasic()
						.and()
						.formLogin().loginPage("/login").defaultSuccessUrl("/")
						.and().logout().invalidateHttpSession(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
						return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
}
