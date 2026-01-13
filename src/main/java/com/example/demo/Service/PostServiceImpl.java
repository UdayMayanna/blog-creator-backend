package com.example.demo.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private CommentRepository commentRepository;
	
	public Post savePost(Post post, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            post.setLikeCount(0);
            post.setViewCount(0);
            post.setDate(new Date());
            post.setUser(user); // Setting the user for the post
            System.out.println(user);
            System.out.println(post);
            return postRepository.save(post);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }
	
	
	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}
	
	public Post getPostById(Long id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
//			User user = userRepository.findById(post.getUserId()).get();
			post.setViewCount(post.getViewCount() + 1);
//			post.setUser(user);
			return postRepository.save(post);
		}else {
			throw new EntityNotFoundException("Post not found");
		}
	}
	
	public void likePost(Long postId) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.setLikeCount(post.getLikeCount() + 1);
			postRepository.save(post);
		}else {
			throw new EntityNotFoundException("Post not found with id : "+postId);
		}
		
	}
	

//	    public void deletePostById(Long postId) {
//	        Optional<Post> optionalPost = postRepository.findById(postId);
//	        if (optionalPost.isPresent()) {
//	            Post post = optionalPost.get();
//	            
//	            // Delete all comments associated with the post
//	            List<Comment> comments = post.getComments();
//	            for (Comment comment : comments) {
//	                commentRepository.delete(comment);
//	            }
//
//	            // Now delete the post itself
//	            postRepository.delete(post);
//	        } else {
//	            throw new EntityNotFoundException("Post not found with id : " + postId);
//	        }
//	    }
	
	public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUser_Id(userId);
    }

	
	
	public List<Post> getPopularPost(){
		List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "likeCount"));
		return posts;
	}
	
	public List<Post> searchByName(String name){
		return postRepository.findAllByNameContaining(name);
	}


	 @Transactional
	    public void deletePostById(Long postId) {
	        // Find the post by its ID
	        Post post = postRepository.findById(postId)
	                                  .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
	        
	        // Retrieve associated comments using the CommentRepository
	        List<Comment> comments = commentRepository.findByPostId(postId);

	        // Delete the comments
	        commentRepository.deleteAll(comments);

	        // Delete the post
	        postRepository.delete(post);
	    }


	@Override
	public Post updatePost(Post post) {
		// TODO Auto-generated method stub
		Optional<Post> optionalPost = postRepository.findById(post.getId());
		
		if(optionalPost.isPresent()) {
			Post existPost = optionalPost.get();
			existPost.setName(post.getName());
			existPost.setContent(post.getContent());
			existPost.setDate(new Date());
			existPost.setImg(post.getImg());
			existPost.setTags(post.getTags());
			
			postRepository.save(existPost);
		}else{
			throw new EntityNotFoundException("Post not found");
		}
		return null;
	}
}
