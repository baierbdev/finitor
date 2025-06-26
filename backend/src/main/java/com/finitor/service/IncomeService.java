package com.finitor.service;


import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.finitor.dto.TransactionRequestDTO;

public interface IncomeService {
	public ResponseEntity<?> createNewIncome(UUID userId, TransactionRequestDTO transactionDTO);
	public ResponseEntity<?> deleteIncome(UUID id);
	public ResponseEntity<?> modifyIncome(TransactionRequestDTO transactionDTO) ;
}
