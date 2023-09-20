package com.app.dto;

import java.util.List;
import java.util.UUID;

import com.app.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
private UUID productId;
private String productName;
private String unit;
private Double price;
private UUID poultryId;
private UUID breedId;
private String poultryName;
private Integer quantity;
private	 List<Product> products;
}
