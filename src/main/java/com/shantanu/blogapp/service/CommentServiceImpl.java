package com.shantanu.blogapp.service;

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
	public Comment addCommentDetails(Post post, Comment comment) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		comment.setCreatedAt(currentTimestamp);
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
		Comment comment = null;
		if(optionalComment.isPresent()) {
			comment = optionalComment.get();
		} else {
			throw new RuntimeException("Did not find the comment id " + id);
		}
		return comment;
	}

	public Comment getOldComment(Comment comment, Comment oldComment) {
		comment.setId(oldComment.getId());
		comment.setText(oldComment.getText());
		return comment;
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
