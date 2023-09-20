package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoultryBreedDTO { 
private UUID id;

private UUID poultryId;
private String poultryName;
private String address;
private String phoneNo;
private String districtName;
private String stateName;
private String countryName;

private UUID breedMappingId;
private UUID breedId;
private String breedName;
private Integer totalCount;
private UUID poultryBreedMappingId;


}
