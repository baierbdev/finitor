package com.finitor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.finitor.dto.TransactionRequestDTO;
import com.finitor.dto.TransactionResponseDTO;
import com.finitor.model.ExpenseEntity;
import com.finitor.model.UserEntity;
import com.finitor.repository.ExpenseRepository;
import com.finitor.repository.UserRepository;
import com.finitor.service.ExpenseService;
import com.finitor.util.DataValidationUtil;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private UserRepository userRepository;

    public List<ExpenseEntity> getAll(UUID userId) {
    	return expenseRepository.findByUserId(userId);
    }	
	
	@Override
	public ResponseEntity<?> createNewExpense(UUID userID,TransactionRequestDTO transactionDTO) {
		Optional<UserEntity> userOptional = userRepository.findById(userID);
		if (!userOptional.isPresent() && userOptional.isEmpty()){
			return ResponseEntity.badRequest().body("Invalid user");
		}

		if (DataValidationUtil.isTransactionDTOValid(transactionDTO)){
			return ResponseEntity.badRequest().body("Missing or null fields");		
		}
		ExpenseEntity transactionForSave = new ExpenseEntity(
				transactionDTO.getName(), 
				transactionDTO.getDescription(),
				transactionDTO.getDate(),
				transactionDTO.getAmount());
		transactionForSave.setUser(userOptional.get());
		expenseRepository.save(transactionForSave);
		return ResponseEntity.ok(new TransactionResponseDTO(transactionForSave));
	}
	
	public ResponseEntity<?> deleteExpense(UUID id) {
		Optional<ExpenseEntity> expenseOptional = expenseRepository.findById(id);
		if (expenseOptional.isPresent()) {
			expenseRepository.deleteById(id);
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<?> modifyExpense(TransactionRequestDTO transactionDTO) {
		Optional<ExpenseEntity> expenseOptional = expenseRepository.findById(transactionDTO.getId());
		if (expenseOptional.isPresent()) {
			ExpenseEntity expenseForSave = expenseOptional.get();
			if (expenseForSave.getDescription() != transactionDTO.getDescription()) {
				expenseForSave.setDescription(transactionDTO.getDescription());
			}
			if (expenseForSave.getName() != transactionDTO.getName()) {
				expenseForSave.setName(transactionDTO.getName());
			}
			if (expenseForSave.getDate() != transactionDTO.getDate()) {
				expenseForSave.setDate(transactionDTO.getDate());
			}
			if (expenseForSave.getAmount() != transactionDTO.getAmount()) {
				expenseForSave.setAmount(transactionDTO.getAmount());
			}
			expenseRepository.save(expenseForSave);
			return  ResponseEntity.ok().body(new TransactionResponseDTO(expenseForSave));
			
		}
		return ResponseEntity.badRequest().build();
	}

}
