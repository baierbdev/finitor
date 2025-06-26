package com.finitor.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id" ,updatable = false)
	private UUID id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "base_salary")
	private BigDecimal baseSalary;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY,orphanRemoval = true)
	private List<IncomeEntity> incomeEntity= new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY,orphanRemoval = true)
	private List<ExpenseEntity> expenseEntity = new ArrayList<>();

	public UserEntity() {
	}
	
	public UserEntity(String username, String email, String password, BigDecimal baseSalary) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.baseSalary = baseSalary;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getId() {
		return id;
	}

	public List<IncomeEntity> getIncomeEntity() {
		return incomeEntity;
	}

	public List<ExpenseEntity> getExpenseEntity() {
		return expenseEntity;
	}

}
