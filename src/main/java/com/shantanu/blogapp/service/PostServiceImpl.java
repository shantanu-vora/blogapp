package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;

	@Override
	public void savePost(Post post) {
		String excerpt = post.getContent().substring(0, 101);
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		post.setAuthor("Shantanu");
		post.setPublished(true);
		post.setPublishedAt(currentTimestamp);
		post.setCreatedAt(currentTimestamp);
		post.setExcerpt(excerpt);
		postRepository.save(post);
	}
}
