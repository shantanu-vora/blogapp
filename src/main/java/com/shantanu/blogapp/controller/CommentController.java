package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.config.UserDetails;
import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.repository.UserRepository;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/post/saveComment/{id}")
	public String saveComment(@PathVariable("id") int id, Comment comment,
														@AuthenticationPrincipal UserDetails currentUser) {
		Post post = postService.getPostById(id);
		post.addComment(commentService.addCommentDetails(post, comment, currentUser));
		postService.saveComment(post);
		return "redirect:/post/{id}";
	}

	@GetMapping("/post/{postId}/comment/{commentId}")
	public String showUpdateCommentForm(@PathVariable("commentId") int commentId,
																			@PathVariable("postId") int postId,
																			@ModelAttribute("post") Post post,
																			@ModelAttribute("comment") Comment comment,
																			@AuthenticationPrincipal UserDetails currentUser) {
		Post postById = postService.getPostById(postId);
		User user = userRepository.findByUsername(currentUser.getUsername()).get();
		if(!currentUser.getUsername().equals(postById.getAuthor()) &&  !user.getRole().equals("ROLE_ADMIN")) {
			throw new RuntimeException("You are not authorized to view this page");
		}
		post.setId(postId);
		Comment oldComment = commentService.getCommentById(commentId);
		commentService.getOldComment(comment, oldComment);
		return "updatecomment";
	}

	@PostMapping("/post/{postId}/updateComment/{commentId}")
	public String updateComment(@PathVariable("commentId") int commentId,
															@ModelAttribute("comment") Comment comment) {
		Comment oldComment = commentService.getCommentById(commentId);
		commentService.updateComment(comment, oldComment);
		return "redirect:/post/{postId}";
	}

	@PostMapping("/post/{postId}/deleteComment/{commentId}")
	public String deleteComment(@PathVariable("postId") int postId,
															@PathVariable("commentId") int commentId,
															@AuthenticationPrincipal UserDetails currentUser) {
		Post postById = postService.getPostById(postId);
		User user = userRepository.findByUsername(currentUser.getUsername()).get();
		if(!currentUser.getUsername().equals(postById.getAuthor()) &&  !user.getRole().equals("ROLE_ADMIN")) {
			throw new RuntimeException("You are not authorized to view this page");
		}
		Comment comment = commentService.getCommentById(commentId);
		commentService.deleteComment(comment);
		return "redirect:/post/{postId}";
	}
}
