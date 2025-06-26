package com.finitor.dto;

import java.math.BigDecimal;

public class UserRequestDTO{
	private String  username;
	private String email;
	private String password;
	private BigDecimal baseSalary;

	public UserRequestDTO() {

	}

	public UserRequestDTO( String username,  String email, String password,  BigDecimal baseSalary) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.baseSalary = baseSalary;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(BigDecimal baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
