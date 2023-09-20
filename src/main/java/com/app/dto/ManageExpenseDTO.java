package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ManageExpenseDTO {
	private UUID id;
	private UUID expenseHeadId;
	private String expenseType;
	private String description;
	@JsonFormat(pattern = "YYYY/MM/dd")
	private Date date;
	private String amount;
	private String poultryName;
	private UUID poultryId;

}
