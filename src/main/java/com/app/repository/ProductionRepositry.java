package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Production;
@Repository
public interface ProductionRepositry extends WriteableRepository<Production, UUID> {

	Production findByBreedIdAndPoultryIdAndCategoryIdAndDate(UUID breedId, UUID poultryId, UUID categoryId, String date);

	Production findCountById(UUID id);

	Production findCountByPoultryIdAndBreedIdAndCategoryId(UUID poultryId, UUID breedId, UUID categoryId);
  
}
