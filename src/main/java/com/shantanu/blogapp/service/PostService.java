package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PostService {
	String savePost(Post post, Tag tag);
	String saveDraft(Post post, Tag tag);
	List<Post> getAllPosts();
	Post getPostById(int id);
	void updatePost(Post post, Tag tag, Post postById);
	void saveComment(Post post);
	Page<Post> findPaginated(int pageNumber, int pageSize, String searchText, String order);
	Page<Post> findPaginatedWithFilter(int pageNumber, int pageSize, String searchText, String order, List<Integer> selectedTags);
	String getRequestParamsForTags(List<Integer> selectedTags);
}
