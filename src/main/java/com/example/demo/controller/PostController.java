package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
// @CrossOrigin(
//     origins = {
//         "http://localhost:4200",
//         "https://blog-creator-awmy71zu2-uday-mayannas-projects.vercel.app"
//     }
// )
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/test")
   public String test() {
	   return "Uday Mayanna";
   }
	
	@PostMapping("/{userId}")
	public ResponseEntity<?> createPost(@Valid @RequestBody Post post, @PathVariable Long userId){
		try {
			Post createdPost = postService.savePost(post,userId);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts(){
		try {
			List<Post> posts = postService.getAllPosts();
		    Collections.reverse(posts); // Reverse the list
		    return ResponseEntity.status(HttpStatus.OK).body(posts);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable("postId") Long postId){
		try {
			Post post = postService.getPostById(postId);
			return ResponseEntity.ok(post);
		} catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<?> updatePost(@Valid @RequestBody Post post){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(post));
		}catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{postId}/like")
	public ResponseEntity<?> likePost(@PathVariable("postId") Long postId){
		try {
			postService.likePost(postId);
			return ResponseEntity.ok(new String[] {"Post liked successfully."});
		}catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<?> searchByName(@PathVariable String name){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postService.searchByName(name));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/popular")
	public ResponseEntity<?> getPopularPost(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postService.getPopularPost());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getPostByUserID(@PathVariable Long userId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUserId(userId));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id){
		try {
			postService.deletePostById(id);
			return ResponseEntity.ok("Post Deleted");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
