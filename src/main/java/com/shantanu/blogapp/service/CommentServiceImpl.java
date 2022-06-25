package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment addCommentDetails(Post post, Comment comment) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		comment.setCreatedAt(currentTimestamp);
		comment.setPostId(post.getId());
		return comment;
//		commentRepository.save(comment);
	}

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
}
