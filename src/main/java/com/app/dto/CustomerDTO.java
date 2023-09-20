package com.app.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
	private UUID id;
	private String name;
	private String address;
	private String postelCode;
	private String email;
	private String phoneNo;
	private UUID poultryId;

}
