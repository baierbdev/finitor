package com.finitor.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.finitor.model.UserEntity;


public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private BigDecimal baseSalary;

    public UserResponseDTO() {}
	
    public UserResponseDTO(UserEntity user) {
	this.id = user.getId();
	this.username = user.getUsername();
	this.email = user.getEmail();
	this.password = user.getPassword();
	this.baseSalary = user.getBaseSalary();
    }

	public UUID getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public BigDecimal getBaseSalary() {
		return baseSalary;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBaseSalary(BigDecimal baseSalary) {
		this.baseSalary = baseSalary;
	}
    
}
