package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Comment;

public interface CommentService {
	Comment createComment(Long postId, Long userId, String content);
	
	List<Comment> getCommentsByPostId(Long postId);
}
