package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.PoultryMapping;
@Repository
public interface PoultryBreedRepositry extends WriteableRepository<PoultryMapping, UUID>{

	PoultryMapping findByPoultryId(UUID uuid);

	int countByPoultryId(UUID poultryId);





}
