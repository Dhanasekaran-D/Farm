package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SalesResponse {
	
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
	private String address;
	private String phoneNo;
	
	private UUID customerId;
	private String name;
	private String addressLine;
	private String phoneNumber;
	private String postalCode;
	private String emailId;
	
	private UUID salesId;
	private String salesNotes;
	private UUID productId;
	private String productName;
	private Integer quantity;
	private String rate;
	private String amount;
	
	

}
