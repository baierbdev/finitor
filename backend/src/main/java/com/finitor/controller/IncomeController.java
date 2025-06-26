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
import com.finitor.model.IncomeEntity;
import com.finitor.service.impl.IncomeServiceImpl;

@RestController
@RequestMapping("/api/user/{userId}/incomes")
public class IncomeController {
	@Autowired
	IncomeServiceImpl  incomeService;
	
	@GetMapping
	public List<IncomeEntity> getAll(@PathVariable(name = "userId") UUID userId) {
		return incomeService.getAll(userId);
	}
	
	@PostMapping
	public ResponseEntity<?> newIncome(@RequestBody TransactionRequestDTO transactionDTO,@PathVariable(name = "userId") UUID userId){
		return incomeService.createNewIncome(userId,transactionDTO);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeIncome(@PathVariable(name = "id") UUID id){
		return incomeService.deleteIncome(id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyIncome(@RequestBody TransactionRequestDTO transactionRequestDTO){
		return incomeService.modifyIncome(transactionRequestDTO);
	}
}
