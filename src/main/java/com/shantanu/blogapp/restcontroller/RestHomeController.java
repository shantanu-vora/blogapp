package com.shantanu.blogapp.restcontroller;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.PostRepository;
import com.shantanu.blogapp.service.PostService;
import com.shantanu.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestHomeController {

	private static final int PAGE_SIZE = 15;

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagService tagService;

	@GetMapping("/posts")
	public List<Post> getPaginatedPosts(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
																			@RequestParam(value = "search", defaultValue = "") String search,
																			@RequestParam(value = "tagId", defaultValue = "") List<Integer> tagIdList,
																			@RequestParam(value = "order", defaultValue = "desc") String order) {
		List<Tag> tagList = tagService.getAllTags();
		if(tagIdList.isEmpty()) {
			for(Tag tag: tagList) {
				tagIdList.add(tag.getId());
			}
		}
		Page<Post> page = postService.getPaginatedPostsWithFilter(pageNumber, PAGE_SIZE, search, order, tagIdList);
		return page.getContent();
	}
}
