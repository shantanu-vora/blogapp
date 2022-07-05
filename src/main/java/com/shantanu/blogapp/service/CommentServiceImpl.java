package com.shantanu.blogapp.service;

import com.shantanu.blogapp.config.UserDetailsImpl;
import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment addCommentDetails(Post post, Comment comment, UserDetailsImpl currentUser) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		comment.setCreatedAt(currentTimestamp);
		if(currentUser != null) {
			comment.setName(currentUser.getUsername());
			comment.setEmail(currentUser.getEmail());
		}
		comment.setPostId(post.getId());
		return comment;
	}

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public Comment getCommentById(int id) {
		Optional<Comment> optionalComment = commentRepository.findById(id);
		Comment comment;
		if(optionalComment.isPresent()) {
			comment = optionalComment.get();
		} else {
			throw new RuntimeException("Did not find the comment id " + id);
		}
		return comment;
	}

	public void getOldComment(Comment comment, Comment oldComment) {
		comment.setId(oldComment.getId());
		comment.setText(oldComment.getText());
	}

	@Override
	public void updateComment(Comment comment, Comment oldComment) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		oldComment.setText(comment.getText());
		oldComment.setUpdatedAt(currentTimestamp);
		commentRepository.save(oldComment);
	}

	@Override
	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
	}
}