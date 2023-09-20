package com.app.service;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.BreedTypeDAO;
import com.app.entity.BreedType;
import com.app.repository.BreedTypeRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class BreedTypeService {
	
	private @NonNull BreedTypeRepository breedTypeRepository;
	
	@Autowired
	BreedTypeDAO breedTypeDAO;

	public void save(BreedType breedType) {
		breedTypeRepository.save(breedType);
		
	}
    

	 public Object getByBreedId(UUID breedId) {
		return breedTypeDAO.findByBreedId(breedId);
		
		
	}

	public Object getById(UUID id) {
		return breedTypeRepository.findById(id);
	}

	public void saveOrUpdate(BreedType breedType) {
		breedTypeRepository.saveAndFlush(breedType);
	}

	public void deleteById(UUID breedTypeId) {
		breedTypeRepository.deleteById(breedTypeId);
	}


	public Object getAll() {
		
		return breedTypeDAO.findAll();
	}


	public BreedType findByName(String breedType) {
		return breedTypeRepository.findByBreedType(breedType);
	}


	public Optional<BreedType> getid(UUID id) {
		return breedTypeRepository.findById(id);
	}


	public BreedType findByNameExcludeId(String breedType, UUID id) {
		return breedTypeDAO.findByNameExcludeId(breedType,id);
	}


	



	
}

