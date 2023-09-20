package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoulrtyBreedGetDTO {
	private UUID poultryId;
	private String PoultryName;
	private UUID breedId;
	private String breedName;

}
