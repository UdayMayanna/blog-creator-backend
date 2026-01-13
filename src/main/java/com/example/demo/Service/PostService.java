package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Post;

public interface PostService {
	Post savePost(Post post, Long userId);
	
    List<Post> getAllPosts();
    
    Post getPostById(Long id);
    
    void likePost(Long postId);
    
    List<Post> searchByName(String name);
    
    List<Post> getPopularPost();
    
    List<Post> getPostsByUserId(Long userId);
    
    void deletePostById(Long postId);

	Post updatePost(Post post);
}
