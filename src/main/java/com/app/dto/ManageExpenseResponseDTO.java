package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManageExpenseResponseDTO {

	private UUID id;
	private UUID expenseHeadId;
	private String expenseType;
	private String description;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private String date;
	private String fromDate;
	private String amount;
	private UUID poultryId;
	private String poultryName;
	private String totalAmount;
}
