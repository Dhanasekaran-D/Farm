package com.app.dto;


import java.util.UUID;

import com.app.enumeration.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoultryResponse {
	private UUID id;
	private String poultryName;
	private String phoneNo;
	private String address;
	private UUID districtId;
	private String districtName;
    private UUID stateId;
    private String stateName;
    private UUID countryId;
    private String countryName;
	}

