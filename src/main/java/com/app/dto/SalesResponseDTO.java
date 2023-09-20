package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesResponseDTO {
	private UUID id;
    private UUID salesId;
	private UUID productId;
	private String productName;
	private Integer quantity;
	private Integer rate;
	private Integer amount;
	

	
	
}
