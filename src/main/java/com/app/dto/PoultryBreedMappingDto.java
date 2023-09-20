package com.app.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoultryBreedMappingDto {
	private UUID id;
	private UUID poultryId;
	private String poultryName;
	private	 List<BrredPoultry> breedList;
}

