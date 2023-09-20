package com.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.DistrictEntity;

@Repository
public interface DistrictRepository extends WriteableRepository<DistrictEntity, UUID> {

	Optional<DistrictEntity> findBydistrictName(String districtName);

	List<DistrictEntity> findByStateId(UUID stateId);

}
