package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bcrypt;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcrypt) {
		this.userRepository = userRepository;
		this.bcrypt = bcrypt;
	}
	
	@Override
	public User saveUser(User user) {
		String encryptedPassword = bcrypt.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		return userRepository.save(user);
	}
	
	@Override
	public User loginUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && bcrypt.matches(password, user.getPassword())) {
			return user;
		} else {
			return null; // or throw an appropriate exception
		}
	}
}
