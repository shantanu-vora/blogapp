package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface PostService {
//	void savePost(Post post, Tag tag, Principal principal);
	void savePost(Post post);
	void saveDraft(Post post, Post oldPost, Tag tag, Principal principal);
	Post getPostById(int id);
//	void updatePost(Post post, Tag tag, Post postById);

	void updatePost(Post post, Post postById);
	void deletePost(Post post);
	void saveComment(Post post);
	Page<Post> getPaginatedPosts(int pageNumber, int pageSize, String searchText, String order);
	Page<Post> getPaginatedPosts(int pageNumber, int pageSize, String searchText, String order, Boolean isPublished);
	Page<Post> getPaginatedPostsWithFilter(int pageNumber, int pageSize, String searchText, String order, List<Integer> selectedTags);
	String getRequestParamsForTags(List<Integer> selectedTags);
}
