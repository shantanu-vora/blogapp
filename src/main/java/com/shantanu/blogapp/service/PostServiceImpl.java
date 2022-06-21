package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;

	@Override
	public void savePost(Post post) {
		postRepository.save(post);
	}
}
