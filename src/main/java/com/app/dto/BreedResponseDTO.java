package com.app.dto;

import java.util.UUID;

import com.app.enumeration.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedResponseDTO {
private UUID id;
private String breedName;
private String descriptions;
private UUID breedTypeId;
private String breedTypeName;
private Status status;
}
