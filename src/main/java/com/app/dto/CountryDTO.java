package com.app.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CountryDTO {
private UUID id;
private String name;
private String shortName;
private String countryCode;

}
