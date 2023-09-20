package com.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.PoultryDAO;
import com.app.dto.PoultryResponse;
import com.app.entity.PoultryEntity;
import com.app.repository.PoultryRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class PoultryService {
	private @NonNull PoultryRepository poultryRepository;

	@Autowired
	private @NonNull PoultryDAO poultryDAO;

	public void savePoultry(PoultryEntity poultry) {
		poultryRepository.save(poultry);
	}

	public void saveOrUpdate(PoultryEntity poultry) {
		poultryRepository.saveAndFlush(poultry);

	}
	public List<PoultryResponse> getAll() {
		return poultryDAO.findAll();
	}

	public List<PoultryResponse> getDistrictById(UUID districtId) {
		return poultryDAO.findByDistrictId(districtId);
	}

	public List<PoultryResponse> getById(UUID id) {
		return poultryDAO.findById(id);
	}

	public Object getAllPoultry(String key) {
		return poultryDAO.getAllPoultry(key);
	}

	public PoultryEntity findByName(String poultryName) {
		return poultryRepository.findByPoultryName(poultryName);
	}

	 public void deletePoultryId(UUID poultryId) {
		 poultryRepository.deleteById(poultryId);
		
	}

	public PoultryEntity findByPoultryAddress(String address) {
		return poultryRepository.findByAddress(address);
	}

	public PoultryEntity findByNameExcludeId(String poultryName, UUID id) {
		return poultryDAO.findByNameExcludeId(poultryName,id);
	}

	public PoultryEntity findByAddressExcludeId(String address, UUID id) {
		return poultryDAO.findByAddressExcludeId(address,id);
	}

	public PoultryEntity getAllPoultryId(UUID poultryId) {
		// TODO Auto-generated method stub
		return poultryDAO.getAllPoultryId(poultryId);
	}

	public PoultryResponse getPoultryId(UUID poultryId) {
		// TODO Auto-generated method stub
		return poultryDAO.getAllPoultrys(poultryId);
	}
//
//	public PoultryEntity getPoultryId(UUID poultryId) {
//		// TODO Auto-generated method stub
//		return poultryDAO.getPoultryId(poultryId);
//	}




}
