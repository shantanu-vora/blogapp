package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@GetMapping("/newPost")
	public String showNewPostPage(Model model) {
		Post post = new Post();
		Tag tag = new Tag();
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		return "newpost";
	}

	@PostMapping("/save")
	public String savePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
		postService.savePost(post);
		tagService.saveTag((tag));
		return "redirect:/post/newPost";
	}

	@GetMapping("/")
	public String showHomePage(Model model) {
		List<Post> postList = postService.getAllPosts();
		model.addAttribute("postList", postList);
		return "home";
	}



}
