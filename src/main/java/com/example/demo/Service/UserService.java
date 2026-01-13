package com.example.demo.Service;

import com.example.demo.Entity.User;

public interface UserService {
	User saveUser(User user);
	
	User loginUser(String email, String password);
}
