package com.finitor.util;

import java.util.stream.Stream;

import com.finitor.dto.TransactionRequestDTO;

public class DataValidationUtil {
	public static boolean isTransactionDTOValid(TransactionRequestDTO transaction) {
		if (Stream.of(transaction.getAmount(),
				transaction.getName(),
				transaction.getDate()).anyMatch(filter -> filter == null)) {
			return true;
		}
		return false;
	}
}
