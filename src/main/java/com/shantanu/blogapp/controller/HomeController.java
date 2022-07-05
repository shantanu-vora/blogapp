package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.entity.User;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

	private static final int PAGE_SIZE = 10;
	@Autowired
	private PostService postService;

	@Autowired
	private  TagService tagService;

	@GetMapping("/")
	public String showHomePage(Model model) {
		return findPaginated(1, model, "", "desc");
	}

	@GetMapping("/post/drafts")
	public String showDraftsPage(Model model, Principal principal) {
		return findPaginated(1, model, "", "desc", false, principal);
	}

	@GetMapping("/search")
	public String searchPosts(@RequestParam("search") String search,
														@RequestParam(value = "order", defaultValue = "desc") String order,
														@RequestParam(value = "tagId", required = false, defaultValue = "") List<Integer> tagIdList,
														Model model) {
		if(tagIdList.isEmpty()) {
			return findPaginated(1, model, search.toLowerCase(), order);
		} else {
			return findPaginatedWithFilter(1, model, search.toLowerCase(), tagIdList, order);
		}
	}

	@GetMapping("/page/{pageNumber}")
	public String findPaginated(@PathVariable("pageNumber") int pageNumber, Model model,
															@RequestParam(value = "search") String search,
															@RequestParam(value = "order", defaultValue = "desc") String order) {
		Page<Post> page = postService.findPaginated(pageNumber, PAGE_SIZE, search, order);
		List<Post> postList = page.getContent();
		List<Tag> tagList = tagService.getAllTags();
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalPosts", page.getTotalElements());
		model.addAttribute("postList", postList);
		model.addAttribute("search", search);
		model.addAttribute("order", order);
		model.addAttribute("tagList", tagList);
		return "home";
	}

	@GetMapping("/page/{pageNumber}/filter")
	public String findPaginatedWithFilter(@PathVariable("pageNumber") int pageNumber, Model model,
																				@RequestParam(value = "search") String search,
																				@RequestParam(value = "tagId", required = false, defaultValue = "") List<Integer> tagIdList,
																				@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		List<Tag> tagList = tagService.getAllTags();
		if(tagIdList.isEmpty()) {
			for(Tag tag: tagList) {
				tagIdList.add(tag.getId());
			}
		}
		Page<Post> page = postService.findPaginatedWithFilter(pageNumber, PAGE_SIZE, search, order, tagIdList);
		List<Post> postList = page.getContent();
		String requestParams = postService.getRequestParamsForTags(tagIdList);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalPosts", page.getTotalElements());
		model.addAttribute("postList", postList);
		model.addAttribute("search", search);
		model.addAttribute("order", order);
		model.addAttribute("tagList", tagList);
		model.addAttribute("requestParams", requestParams);
		model.addAttribute("tagId", tagIdList);
		return "homefilter";
	}

	@GetMapping("/post/drafts/page/{pageNumber}")
	public String findPaginated(@PathVariable("pageNumber") int pageNumber, Model model,
															@RequestParam(value = "search") String search,
															@RequestParam(value = "order", defaultValue = "desc") String order,
															@RequestParam(value = "isPublished", required = false, defaultValue = "false") Boolean isPublished,
															Principal principal) {
		Page<Post> page = postService.findPaginated(pageNumber, PAGE_SIZE, search, order, isPublished);
		List<Post> postList = page.getContent();
		List<Tag> tagList = tagService.getAllTags();
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalPosts", page.getTotalElements());
		model.addAttribute("postList", postList);
		model.addAttribute("search", search);
		model.addAttribute("order", order);
		model.addAttribute("tagList", tagList);
		model.addAttribute("currentUser", principal.getName());
		return "viewdraft";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/signup")
	public String showSignupPage(@ModelAttribute("user") User user) {
		return "signup";
	}
}
