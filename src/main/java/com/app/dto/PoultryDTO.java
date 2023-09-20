package com.app.dto;

import java.util.UUID;

import com.app.enumeration.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoultryDTO {
	private UUID id;
	private String poultryName;
	private String phoneNo; 
	private String address;
    private String districtName;
	
	

}
