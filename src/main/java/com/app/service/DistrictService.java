package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.DistrictDAO;
import com.app.dto.DistrictResponce;
import com.app.entity.DistrictEntity;
import com.app.repository.DistrictRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class DistrictService {
	@Autowired
	private @NonNull DistrictRepository districtRepository;
	private @NonNull DistrictDAO districtDAO;

	public void saveOrUpdate(DistrictEntity object) {
		districtRepository.saveAndFlush(object);
	}

	public Optional<DistrictEntity> findByName(String districtName) {
		return districtRepository.findBydistrictName(districtName);
	}

	public Optional<DistrictEntity> findById(UUID id) {	
		return districtRepository.findById(id);
	}

	public List<DistrictResponce> findAll() {	
		return districtDAO.findAll();
	}

	public List<DistrictEntity>  findDistrictsByStateId(UUID stateId) {	
		return districtRepository.findByStateId(stateId);
	}

	public void save(DistrictEntity district) {	
		districtRepository.saveAndFlush(district);
	}

	public Object getById(UUID id) {
	return districtRepository.findById(id);
	}

	public void deleteById(UUID districtId) {
		districtRepository.deleteById(districtId);
		
	}

	public Object getDistrictsByStateId(UUID stateId) {
		return districtDAO.getDistrictByStateId(stateId);
	}

	public DistrictEntity findByDistrictName(String districtName, UUID id) {
		return districtDAO.findByDistrictName(districtName,id);
	}

	public DistrictEntity findByPinCode(String pinCode, UUID id) {
		return districtDAO.findByPinCode(pinCode,id);
	}

	public void Update(DistrictEntity request) {
		districtRepository.saveAndFlush(request);
		
	}

}
