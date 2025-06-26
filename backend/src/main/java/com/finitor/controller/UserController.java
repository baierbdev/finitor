package com.finitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finitor.dto.UserRequestDTO;
import com.finitor.service.impl.UserServiceImpl;


import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping
	public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
		return userService.getUserByEmail(authHeader);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@PutMapping("/{id}/updateEmail")
	public ResponseEntity<?> modifyEmail(@PathVariable UUID id, @RequestBody UserRequestDTO user) {
		return userService.modifyUser(id, user);
	}

	@PutMapping("/{id}/updatePassword")
	public ResponseEntity<?> modifyPassword(@PathVariable UUID id, @RequestBody UserRequestDTO user) {
		return userService.modifyUser(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
		return userService.deleteUser(id);
	}
}
