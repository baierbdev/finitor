package com.finitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finitor.dto.TokenDTO;
import com.finitor.dto.UserRequestDTO;
import com.finitor.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDTO) {
		return userServiceImpl.createNewUser(userDTO);
	}
	@PostMapping("/login")
	public TokenDTO loginUser(@RequestBody UserRequestDTO userDTO) {
		return userServiceImpl.verifyJwt(userDTO);
	}
}
