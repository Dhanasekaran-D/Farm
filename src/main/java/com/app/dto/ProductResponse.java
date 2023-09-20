package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	private UUID Id;
	private String productName;
	private UUID breedId;
	private String breedName;
	private UUID poultryId;
	private String poultryName;
	private Double price;
	private Integer quantity;
	private UUID categoryId;
	private String categoryName;
	//private String descriptions;
}
