package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@PostMapping("/post/saveComment/{id}")
	public String saveComment(@PathVariable("id") int id, Comment comment) {
		Post post = postService.getPostById(id);
		post.addComment(commentService.addCommentDetails(post, comment));
		postService.saveComment(post);

		return "redirect:/post/{id}";
	}


	@GetMapping("/post/{postId}/comment/{commentId}")
	public String showupdateCommentForm(@PathVariable("commentId") int commentId,
																			@PathVariable("postId") int postId,
																			@ModelAttribute("post") Post post,
																			@ModelAttribute("comment") Comment comment,
																			Model model
																			) {
//		System.out.println(comment);
		post.setId(postId);
		Comment oldComment = commentService.getCommentById(commentId);
//		System.out.println(oldCoLorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas commodi velit repellendus neque! Exercitationem ab eligendi, repellat, facere, molestias ipsa esse repudiandae sequi consequatur eum enim ipsam iure porro magnam!mment);
		commentService.getOldComment(comment, oldComment);

		return "updatecomment";
	}

	@PostMapping("/post/{postId}/updateComment/{commentId}")
	public String updateComment(@PathVariable("commentId") int commentId,
															@ModelAttribute("comment") Comment comment) {
		System.out.println(comment);
		Comment oldComment = commentService.getCommentById(commentId);
//		commentService.updateComment(comment, oldComment);
		System.out.println(oldComment);

		commentService.updateComment(comment, oldComment);
		return "redirect:/post/{postId}";
	}

}
