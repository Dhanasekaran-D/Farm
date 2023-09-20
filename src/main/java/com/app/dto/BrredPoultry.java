package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrredPoultry {
	private UUID id;
	private UUID breedId;
	private String breedName;
	private Integer totalCount;
	private UUID poultryBreedMappingId;
}