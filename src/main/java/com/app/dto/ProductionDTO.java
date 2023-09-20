package com.app.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionDTO {
private UUID id;
private Integer count;
@JsonFormat(pattern = "yyyy/MM/dd")
private String date;
@JsonFormat(pattern = "yyyy/MM/dd")
private Date expiryDate;
private UUID breedId;
private UUID poultryId;
private UUID categoryId;
private Double price;
}
