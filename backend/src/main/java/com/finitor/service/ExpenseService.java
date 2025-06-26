package com.finitor.service;


import java.util.UUID;

import org.springframework.http.ResponseEntity;


import com.finitor.dto.TransactionRequestDTO;

public interface ExpenseService {
	public ResponseEntity<?> createNewExpense(UUID userId, TransactionRequestDTO transactionDTO);
	public ResponseEntity<?> deleteExpense(UUID id);
	public ResponseEntity<?> modifyExpense(TransactionRequestDTO transactionDTO) ;
}
