package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SaleSearchDTO {
	private UUID id;
	private String salesNo;
	private Integer subTotal;
	private Integer discount;
	private Integer totalPayable;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date salesDate;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date deliveryDate;
	private UUID poultryId;
	private String poultryName;
	private UUID customerId;
	private String customerName;
	private String salesNotes;
	private UUID salesId;
	private UUID productId;
	private String productName;
	private Integer quantity;
	private Integer rate;
	private Integer amount;
	
	
}
