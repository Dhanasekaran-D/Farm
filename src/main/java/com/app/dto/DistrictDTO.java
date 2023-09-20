package com.app.dto;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictDTO {
	private UUID id;
	private String districtName;
	private String shortName;
	private UUID stateId;
	private UUID countryId;
//	@OneToOne()
//	//@JoinColumn(name = "district_id")
//	private List<CountryDTO> country;
//	@OneToOne()
//	//@JoinColumn(name = "district_id")
//	private List<StateDTO> state;
}
