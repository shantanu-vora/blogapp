package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;

public interface CommentService {

	Comment addCommentDetails(Post post, Comment comment);

	void saveComment(Comment comment);
}
