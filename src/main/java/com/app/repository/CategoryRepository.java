package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Category;

@Repository
public interface CategoryRepository extends WriteableRepository<Category, UUID> {

	Category findByCategory(String category);

	Category findCategoryById(UUID categoryId);

	

//	Category findByCategoryName(String category);

}
