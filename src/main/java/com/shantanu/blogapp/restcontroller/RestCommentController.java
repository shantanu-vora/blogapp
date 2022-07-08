package com.shantanu.blogapp.restcontroller;

import com.shantanu.blogapp.config.UserDetailsImpl;
import com.shantanu.blogapp.entity.Comment;
import com.shantanu.blogapp.entity.Post;
import com.shantanu.blogapp.exception.CustomException;
import com.shantanu.blogapp.repository.UserRepository;
import com.shantanu.blogapp.service.CommentService;
import com.shantanu.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class RestCommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/{id}/comments")
	public void saveComment(@PathVariable("id") int postId,
													@RequestBody Comment comment,
													@AuthenticationPrincipal UserDetailsImpl currentUser) {
		Post post = postService.getPostById(postId);
		post.addComment(commentService.addCommentDetails(post, comment, currentUser));
		postService.saveComment(post);
	}

	@PutMapping("/{postId}/comments/{commentId}")
	public void updateComment(@PathVariable("postId") int postId,
															@PathVariable("commentId") int commentId,
															@RequestBody Comment comment,
															@AuthenticationPrincipal UserDetailsImpl currentUser) {
		Post oldPost = postService.getPostById(postId);
		Comment oldComment = commentService.getCommentById(commentId);
		if(!currentUser.getUsername().equals(oldPost.getAuthor()) &&  !currentUser.getRole().equals("ROLE_ADMIN")) {
			throw new CustomException("You are not authorized to do this operation");
		}
		if(oldComment.getPostId() != postId) {
			throw new CustomException("Invalid operation. This comment is not associated with this post");
		}
		commentService.updateComment(comment, oldComment, currentUser);
	}

	@DeleteMapping("/{postId}/comments/{commentId}")
	public void deleteComment(@PathVariable("postId") int postId,
															@PathVariable("commentId") int commentId,
															@AuthenticationPrincipal UserDetailsImpl currentUser) {
		Post oldPost = postService.getPostById(postId);
		Comment comment = commentService.getCommentById(commentId);
		if(!currentUser.getUsername().equals(oldPost.getAuthor()) && !currentUser.getRole().equals("ROLE_ADMIN")) {
			throw new CustomException("You are not authorized to do this operation");
		}
		if(comment.getPostId() != postId) {
			throw new CustomException("Invalid operation. This comment is not associated with this post");
		}
		commentService.deleteComment(comment);
	}
}