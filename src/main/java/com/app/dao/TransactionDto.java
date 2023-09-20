package com.app.dao;

import java.util.Date;
import java.util.UUID;

import com.app.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
	private UUID id;
 
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date transactionDate;
	private Integer total;
	private TransactionType transactionType;
	private UUID productId;
	private String productName;
	private UUID poultryId;
	private String poultryName;
}
