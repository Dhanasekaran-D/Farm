package com.app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.BreedUploadDAO;

@Service
public class BreedUploadService {
	
	@Autowired
	BreedUploadDAO breedUploadDAO;

	public Object getAll(UUID breedId) {
		
		return breedUploadDAO.getAll(breedId);
	}
	
	

}
