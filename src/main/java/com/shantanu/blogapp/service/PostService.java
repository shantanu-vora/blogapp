package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;

import java.util.List;

public interface PostService {
	String savePost(Post post, Tag tag);
	String saveDraft(Post post, Tag tag);
	List<Post> getAllPosts();
	Post getPostById(int id);
	void updatePost(Post post, Post postById);

	String savePost(Post post);

	void saveComment(Post post);
}
