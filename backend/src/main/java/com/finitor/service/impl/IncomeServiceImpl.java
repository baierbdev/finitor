package com.finitor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.finitor.dto.TransactionRequestDTO;
import com.finitor.dto.TransactionResponseDTO;
import com.finitor.model.IncomeEntity;
import com.finitor.model.UserEntity;
import com.finitor.repository.IncomeRepository;
import com.finitor.repository.UserRepository;
import com.finitor.service.IncomeService;
import com.finitor.util.DataValidationUtil;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	UserRepository userRepository;

	public List<IncomeEntity> getAll(UUID userId) {
		return incomeRepository.findUserById(userId);
	}

	@Override
	public ResponseEntity<?> createNewIncome(UUID userId, TransactionRequestDTO transactionDTO) {
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent() && userOptional.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid user");
		}
		if (DataValidationUtil.isTransactionDTOValid(transactionDTO)) {
			return ResponseEntity.badRequest().body("Missing or null fields");	
		}
		IncomeEntity transactionForSave = new IncomeEntity(
				transactionDTO.getName(),
				transactionDTO.getDescription(),
				transactionDTO.getDate(),
				transactionDTO.getAmount());

		transactionForSave.setUser(userOptional.get());
		incomeRepository.save(transactionForSave);
		return ResponseEntity.ok(new TransactionResponseDTO(transactionForSave));
	}

	public ResponseEntity<?> deleteIncome(UUID id) {
		Optional<IncomeEntity> expenseOptional = incomeRepository.findById(id);
		if (expenseOptional.isPresent()) {
			incomeRepository.deleteById(id);
			return ResponseEntity.ok("Successfully deleted");
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<?> modifyIncome(TransactionRequestDTO transactionDTO) {
		Optional<IncomeEntity> incomeOptional = incomeRepository.findById(transactionDTO.getId());
		if (incomeOptional.isPresent()) {
			IncomeEntity incomeForSave = incomeOptional.get();
			if (incomeForSave.getDescription() != transactionDTO.getDescription()) {
				incomeForSave.setDescription(transactionDTO.getDescription());
			}
			if (incomeForSave.getName() != transactionDTO.getName()) {
				incomeForSave.setName(transactionDTO.getName());
			}
			if (incomeForSave.getDate() != transactionDTO.getDate()) {
				incomeForSave.setDate(transactionDTO.getDate());
			}
			if (incomeForSave.getAmount() != transactionDTO.getAmount()) {
				incomeForSave.setAmount(transactionDTO.getAmount());
			}
			incomeRepository.save(incomeForSave);
			return ResponseEntity.ok().body(new TransactionResponseDTO(incomeForSave));

		}
		return ResponseEntity.badRequest().body("Missing or null fields");
	}
}
