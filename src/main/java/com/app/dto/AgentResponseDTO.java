package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentResponseDTO {
private UUID id;
private String agentName;
private String email;
private String address;
private String phoneNo;
private UUID districtId;
private String districtName;
private UUID poultryId;
private String poultryName;
private String poultryaddress;
private UUID stateId;
private String stateName;
private UUID countryId;
private String countryName;

}
