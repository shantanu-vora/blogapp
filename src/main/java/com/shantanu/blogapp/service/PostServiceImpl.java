package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		Post post = null;
		if(optionalPost.isPresent()) {
			post = optionalPost.get();
		} else {
			throw new RuntimeException("Did not find post id " + id);
		}
		return post;
	}
}
