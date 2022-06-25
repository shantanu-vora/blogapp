package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@GetMapping("/newPost")
	public String showNewPostPage(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
		return "newpost";
	}

	@PostMapping("/save")
	public String savePost(Post post, Tag tag) {
		postService.savePost(post, tag);
		return "redirect:/post/newPost";
	}

	@PostMapping("/saveDraft")
	public String saveDraft(Post post, Tag tag) {
		postService.saveDraft(post, tag);
		return "redirect:/post/newPost";
	}


	@GetMapping("/")
	public String showHomePage(Model model) {
		List<Post> postList = postService.getAllPosts();
		model.addAttribute("postList", postList);
		return "home";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable("id") int id, Model model) {
		Post post = postService.getPostById(id);
		model.addAttribute("post", post);
		return "viewpost";
	}

	@GetMapping("/edit/{id}")
	public String editPost(@PathVariable("id") int id, Model model) {
		Post post = postService.getPostById(id);
		Tag tag = new Tag();
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		return "newPostEdit";
	}

	@PostMapping("/update")
	public String updatePost(@ModelAttribute("post") Post post) {
		Post postById = postService.getPostById(post.getId());
		postService.updatePost(post, postById);
		return "redirect:/post/";
	}
}
