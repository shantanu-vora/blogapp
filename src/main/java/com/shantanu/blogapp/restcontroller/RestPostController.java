package com.shantanu.blogapp.restcontroller;

import com.shantanu.blogapp.config.UserDetailsImpl;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.exception.CustomException;
import com.shantanu.blogapp.repository.UserRepository;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class RestPostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserRepository userRepository;
	private UserDetailsImpl userDetailsImpl;

	@PostMapping("/posts")
	public void savePost(@RequestBody Post post, Principal principal) {
		if(principal == null) {
			throw new CustomException("You are not authenticated. Identify yourself");
		}
		postService.savePost(post, principal);
	}

	@GetMapping("/posts/{id}")
	public Post viewPost(@PathVariable("id") int id) {
		return postService.getPostById(id);
	}

	@PutMapping("/posts/{id}")
	public void updatePost(@RequestBody Post post,
												 @PathVariable("id") int id,
												 @AuthenticationPrincipal UserDetailsImpl currentUser) {
		Post oldPost = postService.getPostById(id);
		if(currentUser == null) {
			throw new CustomException("You are not authenticated. Identify yourself");
		}
		if(!currentUser.getUsername().equals(oldPost.getAuthor()) &&  !currentUser.getRole().equals("ROLE_ADMIN")) {
			throw new CustomException("You are not authorized to do this operation");
		}
		postService.updatePost(post, oldPost);
	}

	@DeleteMapping("/posts/{id}")
	public String deletePost(@PathVariable("id") int postId,
													 @AuthenticationPrincipal UserDetailsImpl currentUser) {
		Post postById = postService.getPostById(postId);
		if(currentUser == null) {
			throw new CustomException("You are not authenticated");
		}
		if(!currentUser.getUsername().equals(postById.getAuthor()) && !currentUser.getRole().equals("ROLE_ADMIN")) {
			throw new RuntimeException("You are not authorized to do this operation");
		}
		postService.deletePost(postById);
		return "post deleted";
	}
}