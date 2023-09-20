package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.PoultryBreed;

@Repository
public interface BreedMapingRepository extends WriteableRepository<PoultryBreed, UUID> {

	PoultryBreed findByBreedId(UUID breedId);

}
