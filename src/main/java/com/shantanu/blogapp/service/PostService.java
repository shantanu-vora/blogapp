package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;

import java.util.List;

public interface PostService {
	void savePost(Post post);
	List<Post> getAllPosts();
	Post getPostById(int id);
}
