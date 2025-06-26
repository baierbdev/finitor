package com.finitor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TransactionRequestDTO {
	private UUID id;
    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    
    public TransactionRequestDTO() {
    }
    
    public TransactionRequestDTO(String name, BigDecimal amount, String description, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
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
}

