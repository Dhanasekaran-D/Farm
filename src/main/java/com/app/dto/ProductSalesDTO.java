package com.app.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductSalesDTO {
	private UUID id;
	private String salesNo;
	private String subTotal;
	private String discount;
	private String totalPayable;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date salesDate;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date deliveryDate;
	private UUID poultryId;
	private String poultryName;
	private UUID customerId;
	private String customerName;
	private String salesNotes;
	List<SalesResponseDTO> sales;

}
