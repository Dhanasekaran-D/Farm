package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Breed;
@Repository
public interface BreedRepository extends WriteableRepository<Breed,UUID>{

	Breed findByBreedNameAndBreedTypeId(String breedName, UUID breedTypeId);

	Breed findBreedNameById(UUID breedId);
}
