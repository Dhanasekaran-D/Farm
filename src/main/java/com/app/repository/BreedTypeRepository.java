package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.BreedType;

@Repository
public interface BreedTypeRepository  extends WriteableRepository<BreedType,UUID> {

	BreedType findByBreedType(String breedType);

}
