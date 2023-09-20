package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.PoultryEntity;

@Repository
public interface PoultryRepository  extends WriteableRepository<PoultryEntity,UUID> {

	PoultryEntity findByPoultryName(String poultryName);

	PoultryEntity findByAddress(String address);

}