package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CommentService;
import com.example.demo.Entity.Comment;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/comment/create")
	public ResponseEntity<?> createComment(@RequestParam Long postId,@RequestParam Long userId,@Valid @RequestBody String content){
		try {
			return ResponseEntity.ok(commentService.createComment(postId,userId, content));

		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
					
		}
	}
	
	@GetMapping("/comment/{postId}")
	public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
	    try {
	        List<Comment> comments = commentService.getCommentsByPostId(postId);
	        Collections.reverse(comments); // Reverse the list
	        return ResponseEntity.ok(comments);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
	    }
	}
}
