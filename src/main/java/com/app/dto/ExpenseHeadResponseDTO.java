package com.app.dto;

import java.sql.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseHeadResponseDTO {
	private UUID id;
	private String expenseType;
	
}
