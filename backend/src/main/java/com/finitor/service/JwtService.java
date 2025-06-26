package com.finitor.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {
	public String generateToken(String username);
}
