package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DistrictResponce {
	private UUID id;
	private String districtName;
	private String shortName;
	private UUID stateId;
	private String stateName;
	private UUID countryId;
	private String countryName;
}
