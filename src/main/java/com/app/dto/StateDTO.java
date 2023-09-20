package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDTO{
private UUID id;
private String stateName;
private String shortName;
private UUID countryId;
}
