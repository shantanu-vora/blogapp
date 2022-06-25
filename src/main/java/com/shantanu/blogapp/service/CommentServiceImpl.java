package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void saveComment(Comment comment) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		comment.setCreatedAt(currentTimestamp);
		commentRepository.save(comment);
	}
}
