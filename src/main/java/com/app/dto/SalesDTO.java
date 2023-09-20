package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDTO {
	private UUID id;
	private String salesNo;
	
	@JsonFormat(pattern = "YYYY/MM/dd")
	private Date salesDate;
	private String customerName;
	private String totalPayable;
	private UUID poultryId;
	private String poultryName;

}
