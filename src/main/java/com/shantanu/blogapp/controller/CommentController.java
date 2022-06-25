package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/saveComment/{id}")
	public String saveComment(@PathVariable("id") int id, @ModelAttribute("comment") Comment comment) {
		comment.setPostId(id);
		commentService.saveComment(comment);
		return "redirect:/post/{id}";
	}

}
