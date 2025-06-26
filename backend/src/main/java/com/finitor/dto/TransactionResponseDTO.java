package com.finitor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.finitor.model.ExpenseEntity;
import com.finitor.model.IncomeEntity;

public class TransactionResponseDTO {
	private UUID id;
    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    
    public  TransactionResponseDTO() {
		
    }
    
    public TransactionResponseDTO(String name, BigDecimal amount, String description, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
    public TransactionResponseDTO(ExpenseEntity expenseEntity) {
    	this.id = expenseEntity.getId();
    	this.name = expenseEntity.getName();
    	this.amount = expenseEntity.getAmount();
    	this.date = expenseEntity.getDate();
    	this.description = expenseEntity.getDescription();
    }
    
    public TransactionResponseDTO(IncomeEntity incomeEntity) {
    	this.id = incomeEntity.getId();
    	this.name = incomeEntity.getName();
    	this.amount = incomeEntity.getAmount();
    	this.date = incomeEntity.getDate();
    	this.description = incomeEntity.getDescription();
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
