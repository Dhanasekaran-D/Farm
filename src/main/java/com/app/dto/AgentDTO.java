package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDTO {
	
	private UUID id;
	private String agentName;
	private String emailId;
	private String phoneNo;
	private String address;
	private UUID roleId;
	private UUID districtId;
    private UUID poultryId;
	private UUID stateId;
	private UUID countryId;
}
