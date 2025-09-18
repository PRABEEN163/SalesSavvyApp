package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private UserService userservice;
	
	@Autowired
	public UserController(UserService userservice) {
		super();
		this.userservice = userservice;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> rigisterUser(@RequestBody User user) {
		try {
		User registeredUser = userservice.RegisterUser(user);
		return ResponseEntity.ok(Map.of("message", "User registered sucessfully", "user" , registeredUser));
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		}
	}
	
}
