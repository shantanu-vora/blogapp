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

import java.sql.Timestamp;

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
		String excerpt = post.getContent().substring(0, 101);
		post.setAuthor("Shantanu");
		post.setPublished(true);
		post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
		post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		post.setExcerpt(excerpt);
		tag.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		postService.savePost(post);
		tagService.saveTag((tag));
		return "redirect:/post/newPost";
	}
}
