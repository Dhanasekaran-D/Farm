package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
	private UUID id;
	private String name;
	private String address;
	private String postelCode;
	private String email;
	private String phoneNo;
	private UUID districtId;
	private String districtName;
	private UUID stateId;
	private String stateName;
	private UUID countryId;
	private String countryName;
}
