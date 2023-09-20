package com.app.dto;

import java.util.UUID;

import com.app.enumeration.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeerdTypeDTO {
	private UUID id;
	private String breedType;
	private Status status;
}
