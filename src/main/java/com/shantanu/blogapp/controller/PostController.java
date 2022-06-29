package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@Autowired
	private CommentService commentService;

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
		Post postById = postService.getPostById(post.getId());
		postService.saveDraft(post, tag);
		return "redirect:/post/drafts";
	}


	@GetMapping("/")
	public String showHomePage(Model model) {
//		List<Post> postList = postService.getAllPosts();
//		model.addAttribute("postList", postList);
//		return "home";
		return findPaginated(1, model, "", "desc");
	}

	@GetMapping("/drafts")
	public String showDraftsPage(Model model) {
		List<Post> postList = postService.getAllPosts();
		model.addAttribute("postList", postList);
		return "viewdraft";
	}

	@GetMapping("/{id}")
	public String viewPost(@PathVariable("id") int id, Model model, @ModelAttribute("comment") Comment comment) {
		Post post = postService.getPostById(id);
		model.addAttribute("post", post);
		return "viewpost";
	}

	@GetMapping("/edit/{id}")
	public String editPost(@PathVariable("id") int id, Model model) {
		Post post = postService.getPostById(id);
		Tag tag = new Tag();
		List<Tag> tagList = post.getTags();
		List<String> tagNames = new ArrayList<>();
		for(Tag theTag: tagList) {
			tagNames.add(theTag.getName());
		}
		String tags = String.join(",", tagNames);
		tag.setName(tags);
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		return "newPostEdit";
	}

	@PostMapping("/update")
	public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
		Post postById = postService.getPostById(post.getId());
		postService.updatePost(post, tag, postById);
		return "redirect:/post/";
	}

	@GetMapping("/search")
	public String searchPosts(@RequestParam("search") String searchText, Model model) {
//		List<Post> searchedList = postService.getByKeyword(searchText.toLowerCase());
		System.out.println(searchText);
//		List<Tag> tagList = model.getAttribute("tagList");
//		model.addAttribute("postList", searchedList);
		return findPaginated(1, model, searchText.toLowerCase(), "desc");
	}

	@GetMapping("/page/{pageNumber}")
	public String findPaginated(@PathVariable("pageNumber") int pageNumber, Model model,
															@RequestParam("searchText") String searchText,
															@RequestParam("order") String order) {
		int pageSize = 10;
		System.out.println(searchText);
		Page<Post> page = postService.findPaginated(pageNumber, pageSize, searchText, order);

		List<Tag> tagList = tagService.getAllTags();
		List<Post> listPosts = page.getContent();

		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalPosts", page.getTotalElements());
		model.addAttribute("postList", listPosts);
		model.addAttribute("searchText", searchText);
		model.addAttribute("order", order);
		model.addAttribute("tagList", tagList);
		return "home";

	}
}
