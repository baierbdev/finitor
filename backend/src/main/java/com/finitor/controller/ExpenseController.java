package com.finitor.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finitor.dto.TransactionRequestDTO;
import com.finitor.model.ExpenseEntity;
import com.finitor.service.impl.ExpenseServiceImpl;

@RestController
@RequestMapping("/api/user/{userId}/expenses")
public class ExpenseController {
	 
	@Autowired
	ExpenseServiceImpl expenseService;
	
	@GetMapping
	public List<ExpenseEntity> getAll(@PathVariable(name = "userId") UUID userId) {
		System.out.println(userId);
		return expenseService.getAll(userId);
	}
	@PostMapping
	public ResponseEntity<?> newExpense(@RequestBody TransactionRequestDTO transactionDTO, @PathVariable(name = "userId") UUID userId){
		return expenseService.createNewExpense(userId,transactionDTO);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeExpense(@PathVariable(name = "id") UUID id){
		return expenseService.deleteExpense(id);
	}
	@PutMapping
	public ResponseEntity<?> modifyExpense(@RequestBody TransactionRequestDTO transactionRequestDTO){
		return expenseService.modifyExpense(transactionRequestDTO);
	}
}
