package com.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.PoultryBreedDAO;
import com.app.dto.PoultryBreedDTO;
import com.app.entity.PoultryBreed;
import com.app.entity.PoultryMapping;
import com.app.repository.BreedMapingRepository;
import com.app.repository.PoultryBreedRepositry;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class PoultryBreedService {
	private @NonNull PoultryBreedRepositry poultryBreedRepositry;
	
	private @NonNull PoultryBreedDAO poultryBreedDAO;
	private @NonNull BreedMapingRepository breedMapingRepository;


	

	 public void saveOrUpdate(PoultryMapping poultryMapping) throws Exception {
	      
	        boolean isPoultryIdUnique = poultryBreedRepositry.countByPoultryId(poultryMapping.getPoultryId()) == 0;
	        if (!isPoultryIdUnique) {
	            throw new Exception("Poultry  already exists");
	        }
	       
	        Set<UUID> breedIds = new HashSet<>();
	        for (PoultryBreed poultryBreed : poultryMapping.getPoultryBreed()) {
	            UUID breedId = poultryBreed.getBreedId();
	            if (breedIds.contains(breedId)) {
	                throw new Exception("Duplicate breed  found for the same poultry");
	            }
	            breedIds.add(breedId);
	        }
	       
	        poultryBreedRepositry.saveAndFlush(poultryMapping);
	    }

	public void deleteBreed(UUID poultryBreedId) {
		poultryBreedRepositry.deleteById(poultryBreedId);

	}

	public Object getAll() {
		
		return poultryBreedDAO.get();
	}

	public Object getId(UUID id) {
		return poultryBreedDAO.findByPoultryId(id);
	}

	public List<PoultryBreedDTO> getPoultryBreedByPoultryId(UUID breedMappingId) {
		return poultryBreedDAO.getPoultryBreedByPoultryId(breedMappingId);
	}

	public Object getAllPoultryBreed(String poultryName) {
		return poultryBreedDAO.getPoultryBreed(poultryName);
	}

	public PoultryMapping findByName(UUID uuid) {
		// TODO Auto-generated method stub
		return poultryBreedRepositry.findByPoultryId(uuid);
	}

	public PoultryBreed findByBreed(UUID breedId) {
		// TODO Auto-generated method stub
		return breedMapingRepository.findByBreedId(breedId);
	}

	public void Update(PoultryMapping poultryMapping) {
		
	    
		poultryBreedRepositry.saveAndFlush(poultryMapping);
		
	}

	public Object countByPoultryId(UUID poultryId, UUID id) {
		return poultryBreedDAO.countByPoultryId(poultryId, id);
	}

	
	public PoultryBreed getBreed(UUID poultryId) {
		return poultryBreedDAO.getBreed(poultryId);
	}

	public void deleteBreedId(UUID id) {
		breedMapingRepository.deleteById(id);
		
		
	}

	public Object getByPoultryId(UUID poultryId) {
		return poultryBreedDAO.getById(poultryId);
	}

	

	
 
}
