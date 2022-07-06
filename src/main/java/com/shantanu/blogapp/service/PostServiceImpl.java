package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.PostRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.security.Principal;
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
	public void savePost(Post post, Tag tag, Principal principal) {
		String username = principal.getName();
		post.setPublished(true);
		setPostFields(post, username);
		enterTags(post, tag);
		try {
			postRepository.save(post);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveDraft(Post post, Post oldPost, Tag tag, Principal principal) {
		String username = principal.getName();
		post.setPublished(false);
		setPostFields(post, username);
		if(oldPost != null) {
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			post.setPublishedAt(oldPost.getPublishedAt());
			post.setCreatedAt(oldPost.getCreatedAt());
			post.setUpdatedAt(currentTimestamp);
			post.setAuthor(oldPost.getAuthor());
		}
		enterTags(post, tag);
		try {
			postRepository.save(post);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatePost(Post post, Tag tag, Post oldPost) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		List<Comment> commentList = oldPost.getComments();
		String username = oldPost.getAuthor();
		post.setPublished(oldPost.isPublished());
		setPostFields(post, username);
		post.setCreatedAt(oldPost.getCreatedAt());
		post.setPublishedAt(oldPost.getPublishedAt());
		post.setUpdatedAt(currentTimestamp);
		post.setComments(commentList);
		enterTags(post, tag);
		postRepository.save(post);
	}

	@Override
	public void deletePost(Post post) {
		postRepository.delete(post);
	}

	void setPostFields(Post post, String username) {
		String excerpt = post.getContent().length() < 150 ?
										 post.getContent() :
										 post.getContent().substring(0, 150)+"...";
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		post.setAuthor(username);
		post.setPublishedAt(currentTimestamp);
		post.setCreatedAt(currentTimestamp);
		post.setExcerpt(excerpt);
	}

	@Override
	public Post getPostById(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		Post post;
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

	@Override
	public Page<Post> getPaginatedPosts(int pageNumber, int pageSize, String searchText, String order) {
		Pageable pageable;
		if(order.equals("desc")) {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").descending());
		} else {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").ascending());
		}
		return this.postRepository.findBySearchKeyword(pageable, searchText);
	}

	@Override
	public Page<Post> getPaginatedPosts(int pageNumber, int pageSize, String searchText, String order, Boolean isPublished) {
		Pageable pageable;
		if(order.equals("desc")) {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").descending());
		} else {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").ascending());
		}
		return this.postRepository.findBySearchKeyword(pageable, searchText, isPublished);
	}

	@Override
	public Page<Post> getPaginatedPostsWithFilter(int pageNumber, int pageSize, String searchText,
																								String order, List<Integer> tagIdList) {
		Pageable pageable;
		if(order.equals("desc")) {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").descending());
		} else {
			pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("published_at").ascending());
		}
		return this.postRepository.findByFilteredTags(pageable, searchText, tagIdList);
	}

	@Override
	public String getRequestParamsForTags(List<Integer> selectedTags) {
		String requestParam = "";
		for(Integer id: selectedTags) {
			requestParam += ("&tagId=" + id);
		}
		return requestParam;
	}
}