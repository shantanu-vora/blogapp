package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.PostRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagService tagService;

	@Override
	public String savePost(Post post, Tag tag) {
		post.setPublished(true);
		setPostFields(post);
		enterTags(post, tag);
		try {
			postRepository.save(post);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			return "redirect:/post/newPost";
		}
		return null;
	}

	@Override
	public String saveDraft(Post post, Tag tag) {
		post.setPublished(false);
		setPostFields(post);
		enterTags(post, tag);
		try {
			postRepository.save(post);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			return "redirect:/post/newPost";
		}
		return null;
	}

	@Override
	public void updatePost(Post post, Tag tag, Post oldPost) {
		List<Comment> commentList = oldPost.getComments();
		post.setPublished(true);
		setPostFields(post);
		post.setCreatedAt(oldPost.getCreatedAt());
		post.setComments(commentList);
		enterTags(post, tag);
		postRepository.save(post);
	}

	void setPostFields(Post post) {
		String excerpt = post.getContent().length() < 150 ?
										 post.getContent() :
										 post.getContent().substring(0, 150)+"...";
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		post.setAuthor("Shantanu");
		post.setPublishedAt(currentTimestamp);
		post.setCreatedAt(currentTimestamp);
		post.setExcerpt(excerpt);
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

	@Override
	public void saveComment(Post post) {
		postRepository.save(post);
	}

	public void enterTags(Post post, Tag tag) {
		List<String> tagsList = Arrays.asList(tag.getName().split(","));
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

		for(String tagName: tagsList) {
			Tag theTag = new Tag();
			Tag newTag = tagService.getTagByName(tagName);
			tag.getPosts().add(post);
			if (newTag == null) {
				theTag.setName(tagName);
				theTag.setCreatedAt(currentTimestamp);
				tagService.saveTag(theTag);
				post.getTags().add(theTag);
			} else {
				post.getTags().add(newTag);
			}
		}
	}

	public List<Post> getByKeyword(String keyword) {
		return postRepository.findByKeyword(keyword);
	}
}