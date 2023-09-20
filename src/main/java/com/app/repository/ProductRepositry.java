package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Product;
@Repository
public interface ProductRepositry extends WriteableRepository<Product, UUID>{

	Product findByBreedIdAndCategoryIdAndPoultryId(UUID breedId, UUID categoryId, UUID poultryId);



}
