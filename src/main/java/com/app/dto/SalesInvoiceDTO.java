package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesInvoiceDTO {
//private UUID id;
private UUID poultryId;
private String poultryName;
private String poultryAddress;
private String phoneNo;

private UUID customerId;
private String customerName;
private String customerAddress;
private String phoneNumber;

private UUID salesId;
private String salesNo;
private Integer quantity;
private Double rate;
private Double amount;


private UUID productId;
private String productName;

private UUID productSalesId;
private Date salesDate;
private Date DeliveryDate;
private Double subTotal;
private Integer discount;
private Double totalPayable;





}
