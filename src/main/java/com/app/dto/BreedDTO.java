package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BreedDTO {
	private UUID id;
	private String breedName;
	private String breedType;
	private Integer breedCount;
	private String poultryName;
	private String address;
	private String phoneNo;
	private String districtName;
	private String stateName;
	private String countryName;
	
	
}
