package com.finitor.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finitor.model.UserEntity;
import com.finitor.model.UserPrincipal;
import com.finitor.repository.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		if (!userOptional.isPresent()) {
			 throw new UsernameNotFoundException("User Not Found");
		}
		return new UserPrincipal(userOptional.get());
	}
}
