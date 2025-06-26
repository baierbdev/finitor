package com.finitor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finitor.dto.TokenDTO;
import com.finitor.dto.UserRequestDTO;
import com.finitor.dto.UserResponseDTO;
import com.finitor.model.UserEntity;
import com.finitor.repository.UserRepository;
import com.finitor.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JwtServiceImpl jwtServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;


	@Override
	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	@Override
	public ResponseEntity<?> createNewUser(UserRequestDTO user) {
		if (user == null || Stream.of(
				user.getUsername(),
				user.getEmail(),
				user.getPassword()).anyMatch(filter -> filter == null || filter.isBlank())) {
			return ResponseEntity.badRequest().body("Missing required fields");
		}

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registed");
		}
		UserEntity userForSave = new UserEntity(user.getUsername(), user.getEmail(),
				bCryptPasswordEncoder.encode((user.getPassword())), user.getBaseSalary());
		userRepository.save(userForSave);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(userForSave));
	}

	@Override
	public ResponseEntity<UserEntity> getUserById(UUID id) {
		Optional<UserEntity> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<UserEntity> getUserByEmail(String authHeader) {
		String token = authHeader.substring(7);
		String email = jwtServiceImpl.extractUserName(token);
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);

		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@Override
	public ResponseEntity<UserEntity> modifyUser(UUID id, UserRequestDTO user) {
		Optional<UserEntity> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		UserEntity userForSave = userOptional.get();
		if (user.getEmail() != null) {
			userForSave.setEmail(user.getEmail());
		}
		if (user.getPassword() != null) {
			userForSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		userRepository.save(userForSave);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Override
	public ResponseEntity<UserEntity> deleteUser(UUID id) {
		Optional<UserEntity> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			userRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@Override
	public ResponseEntity<?> updateEmail(UUID id, String email) {
		Optional<UserEntity> userOptional = userRepository.findById(id);
		if (userOptional.isPresent() && email != null) {
			UserEntity userForSave = userOptional.get();
			userForSave.setEmail(email);
			userRepository.save(userForSave);
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	public TokenDTO verifyJwt(UserRequestDTO user) {
		Authentication authetication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

		if (authetication.isAuthenticated()) {
			return jwtServiceImpl.getTokenJson(user.getEmail());
		} else {
			return null;
		}
	}

}
