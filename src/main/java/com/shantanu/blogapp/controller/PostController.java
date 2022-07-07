package com.shantanu.blogapp.controller;

import com.shantanu.blogapp.config.UserDetailsImpl;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.UserRepository;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserRepository userRepository;

	private UserDetailsImpl userDetailsImpl;

//	@GetMapping("/newPost")
//	public String showNewPostPage(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
//		return "newpost";
//	}

//	@PostMapping("/save")
//	public String savePost(Post post, Tag tag, Principal principal) {
//		postService.savePost(post, tag, principal);
//		return "redirect:/";
//	}

	@PostMapping("/posts")
	public void savePost(@RequestBody Post post) {
		postService.savePost(post);
	}

	@PostMapping("/saveDraft")
	public String saveDraft(Post post, Tag tag, Principal principal) {
		Post postById;
		try {
			postById = postService.getPostById(post.getId());
			post.setComments(postById.getComments());
			postService.saveDraft(post, postById, tag, principal);
		} catch(RuntimeException e) {
			e.printStackTrace();
			postService.saveDraft(post, null, tag, principal);
		}
		return "redirect:/post/drafts";
	}

	@GetMapping("/posts/{id}")
	public Post viewPost(@PathVariable("id") int id
//												 @ModelAttribute("comment") Comment comment,
//												 @AuthenticationPrincipal UserDetailsImpl currentUser
											) {
//		Model model,
		Post post = postService.getPostById(id);
//		model.addAttribute("post", post);
//		if(currentUser != null) {
//			model.addAttribute("currentUser", currentUser.getUsername());
//		}
//		return "viewpost";
		return post;
	}

//	@GetMapping("/edit/{id}")
//	public String showEditPostPage(@PathVariable("id") int id, Model model,
//																 @AuthenticationPrincipal UserDetailsImpl currentUser) {
//		Post post = postService.getPostById(id);
//		User user = userRepository.findByUsername(currentUser.getUsername()).get();
//		if(!currentUser.getUsername().equals(post.getAuthor()) &&  !user.getRole().equals("ROLE_ADMIN")) {
//			throw new RuntimeException("You are not authorized to view this page");
//		}
//		Tag tag = new Tag();
//		List<Tag> tagList = post.getTags();
//		List<String> tagNames = new ArrayList<>();
//		for(Tag theTag: tagList) {
//			tagNames.add(theTag.getName());
//		}
//		tag.setName(String.join(",", tagNames));
//		model.addAttribute("post", post);
//		model.addAttribute("tag", tag);
//		return "newPostEdit";
//	}

//	@PostMapping("/update/{id}")
//	public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
//		Post postById = postService.getPostById(post.getId());
//		postService.updatePost(post, tag, postById);
//		return "redirect:/post/{id}";
//	}

	@PutMapping("/posts/{id}")
	public void updatePost(@RequestBody Post post, @PathVariable("id") int id) {
		Post oldPost = postService.getPostById(id);
		System.out.println(oldPost.getTitle());
		postService.updatePost(post, oldPost);
	}

//	@PostMapping("/delete/{id}")
//	public String deletePost(@PathVariable("id") int postId, @AuthenticationPrincipal UserDetailsImpl currentUser) {
//		Post postById = postService.getPostById(postId);
//		User user = userRepository.findByUsername(currentUser.getUsername()).get();
//		if(!currentUser.getUsername().equals(postById.getAuthor()) && !user.getRole().equals("ROLE_ADMIN")) {
//			throw new RuntimeException("You are not authorized to view this page");
//		}
//		postService.deletePost(postById);
//		return "redirect:/";
//	}

	@DeleteMapping("/posts/{id}")
	public String deletePost(@PathVariable("id") int postId) {
		Post postById;
		try {
			postById = postService.getPostById(postId);
//		User user = userRepository.findByUsername(currentUser.getUsername()).get();
//		if(!currentUser.getUsername().equals(postById.getAuthor()) && !user.getRole().equals("ROLE_ADMIN")) {
//			throw new RuntimeException("You are not authorized to view this page");
//		}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		postService.deletePost(postById);
		return "post deleted";
	}

}
