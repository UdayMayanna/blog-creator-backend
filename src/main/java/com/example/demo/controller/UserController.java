package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	public UserService userService;

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
		try {
			return ResponseEntity.ok(userService.saveUser(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{email}/{password}")
	public ResponseEntity<User> loginUser(
			@PathVariable String email,
			@PathVariable String password) {

		User user = userService.loginUser(email, password);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(user);
	}

}
