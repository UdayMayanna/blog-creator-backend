package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllByNameContaining(String name);

	List<Post> findAll(Sort sort);
	
	List<Post> findByUserId(Long userId);
	
	List<Post> findByUser_Id(Long userId);
}
