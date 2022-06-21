package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/newPost")
	public String showNewPostPage(Model model) {
		Post post = new Post();
		Tag tag = new Tag();
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		return "newpost";
	}

	@PostMapping("/save")
	public String savePost(@ModelAttribute("post") Post post) {
		postService.savePost(post);
		return "redirect:/newPost";
	}
}
