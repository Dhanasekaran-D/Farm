package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductionRespose {
	private UUID id;
	private UUID breedId;
	private String breedName;
	private Integer count;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date date;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date expiryDate;
	private UUID poultryId;
	private String poultryName;
	private UUID categoryId;
	private String categoryName;
	
}
