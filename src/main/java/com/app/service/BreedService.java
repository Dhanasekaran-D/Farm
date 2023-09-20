package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.BreedDAO;
import com.app.entity.Breed;
import com.app.repository.BreedRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class BreedService {

	private @NonNull BreedRepository breedRepository;

	@Autowired
	private @NonNull BreedDAO breedDAO;

	public void saveBreed(Breed breed) {
		breedRepository.save(breed);
	}

	public void saveOrUpdate(Breed breed) {
		breedRepository.saveAndFlush(breed);

	}

	public List<Breed> findAll() {
		return breedRepository.findAll();
	}
	public void deleteBreed(UUID breedId) {

		breedRepository.deleteById(breedId);
	}

	public Object getAll() {
		return breedDAO.findAll();
	}

	public Object getId(UUID id) {

		return breedDAO.findById(id);
	}

	public Breed findByName(String breedName, UUID breedTypeId) {
		return breedRepository.findByBreedNameAndBreedTypeId(breedName,breedTypeId);
	}

	public Optional<Breed> get(UUID id) {
		
		return breedRepository.findById(id);
	}

	public Breed findByNameExcludeId(String breedName, UUID id, UUID breedTypeId) {
		return breedDAO.findByNameExcludeId(breedName,breedTypeId,id);
	}

	
}
