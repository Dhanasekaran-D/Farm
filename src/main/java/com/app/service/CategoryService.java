package com.app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CategoryDAO;
import com.app.entity.Category;
import com.app.repository.CategoryRepository;

import lombok.AllArgsConstructor;
@Transactional
@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CategoryService {
	@Autowired
	private @NonNull CategoryRepository categoryRepository;
	
	@Autowired
	private @NonNull CategoryDAO categoryDAO; 

	public void saveCategory(Category category) {
		categoryRepository.save(category);

	}

	public Object getAll() {
		return categoryDAO.findAll();
	}

	public Object getById(UUID id) {

		return categoryRepository.findById(id);
	}

	public void saveorUpdate(Category category) {
		categoryRepository.saveAndFlush(category);

	}

	public void deleteById(UUID id) {
		categoryRepository.deleteById(id);

	}

	public Category findByName(String category) {
		return categoryRepository.findByCategory(category);
	}

	public Optional<Category> get(UUID id) {
		return categoryRepository.findById(id);
	}

	public Category findByNameExcludeId(String category, UUID id) {
		return categoryDAO.findByNameExcludeId(category,id);
	}
}
