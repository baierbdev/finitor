package com.finitor.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import com.finitor.dto.UserRequestDTO;
import com.finitor.model.UserEntity;

public interface UserService {
	public List<UserEntity> getAll();
	public ResponseEntity<?> createNewUser(UserRequestDTO user );
	public ResponseEntity<?> getUserById(UUID id);
	public ResponseEntity<?> deleteUser(UUID id);
	public ResponseEntity<?> updateEmail(UUID id, String email);
	public ResponseEntity<?> modifyUser(UUID id, UserRequestDTO user);
	public ResponseEntity<UserEntity> getUserByEmail(String email);
}
