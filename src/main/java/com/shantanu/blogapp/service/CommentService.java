package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;

public interface CommentService {

	Comment addCommentDetails(Post post, Comment comment);

	void saveComment(Comment comment);

	Comment getCommentById(int commentId);


	Comment getOldComment(Comment comment, Comment oldComment);

	void updateComment(Comment comment, Comment oldComment);

	void deleteComment(Comment comment);
}
