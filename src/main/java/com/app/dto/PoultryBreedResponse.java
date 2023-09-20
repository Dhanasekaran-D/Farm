package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PoultryBreedResponse {
	private UUID id;
	private UUID poultryId;
	private String poultryName;

	private Integer totalCount;


}
