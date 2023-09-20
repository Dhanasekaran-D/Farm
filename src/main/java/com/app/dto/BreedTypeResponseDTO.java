package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedTypeResponseDTO {
	private UUID id;

	private String breedType;

	private String breedcount;

	private UUID breedId;

	private String breedName;

	private UUID poultryId;

	private String poultryName;

}
