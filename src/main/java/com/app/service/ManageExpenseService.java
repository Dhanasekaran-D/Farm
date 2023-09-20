package com.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ManageExpenseDAO;
import com.app.dto.ManageExpenseDTO;
import com.app.dto.ManageExpenseResponseDTO;
import com.app.entity.ManageExpense;
import com.app.repository.ManageExpenseRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })

public class ManageExpenseService {

	private @NonNull ManageExpenseRepository manageExpenseRepository;
	
	@Autowired
	ManageExpenseDAO manageExpenseDAO;

	public void save(ManageExpense manage) {
		if (manage.getExpenseHeadId().equals(UUID.fromString("1d007f5c-087f-4415-b98a-ed4b49474d1b"))) {
		    String[] dateParts = manage.getDate().split("/");
		    int month = Integer.parseInt(dateParts[1]);
		    int year = Integer.parseInt(dateParts[0]);
		    UUID poultryId = manage.getPoultryId();
		    UUID expenseHeadId=manage.getExpenseHeadId();
		    if (manageExpenseRepository.existsByMonthAndYearAndPoultryId(month, year, poultryId, expenseHeadId)) {
		        throw new RuntimeException("An expense has already been created for the given month and This poultry");
		    }
		} 
		manageExpenseRepository.save(manage);
	}
	public Object getAll() {
		return manageExpenseDAO.findAll();
	}

	public Object getById(UUID id) {
		return manageExpenseDAO.findById(id);
	}

	public Object getByPoultryId(UUID poultryId) {
		return manageExpenseDAO.findByPoultryId(poultryId);
	}

	public List<ManageExpenseResponseDTO> getByexpenseId(UUID poultryId) {
		return manageExpenseDAO.findByExpenseHeadId(poultryId);
	}

	public void update(ManageExpense manage) {
		if (manage.getExpenseHeadId().equals(UUID.fromString("1d007f5c-087f-4415-b98a-ed4b49474d1b"))) {
		    String[] dateParts = manage.getDate().split("/");
		    int month = Integer.parseInt(dateParts[1]);
		    int year = Integer.parseInt(dateParts[0]);
		    UUID poultryId = manage.getPoultryId();
		    UUID expenseHeadId=manage.getExpenseHeadId();
		    if (manageExpenseRepository.existsByMonthAndYearAndPoultryIdExcludeId(month, year, poultryId, expenseHeadId,manage.getId())) {
		        throw new RuntimeException("An expense has already been created for the given month and This poultry");
		    }
		} 
	    manageExpenseRepository.save(manage);
	}




	public void deleteById(UUID id) {
		manageExpenseRepository.deleteById(id);

	}

	public List<ManageExpenseDTO> getAllBreed(String poultryName,String startDate,String endDate) {
		
		return manageExpenseDAO.getAllBreeds(poultryName,startDate,endDate);
	}

	public ManageExpense findByExpenseHeadIdAndPoultryIdAndDate(UUID expenseHeadId, UUID poultryId, String date) {
		return manageExpenseRepository.findByExpenseHeadIdAndPoultryIdAndDate(expenseHeadId, poultryId, date);
	}

	public ManageExpense findByExpenseHeadIdAndPoultryIdAndDateExcludeId(UUID expenseHeadId, UUID poultryId,
			String date, UUID id) {
		return manageExpenseDAO.findByExpenseHeadIdAndPoultryIdAndDateExcludeId(expenseHeadId, poultryId, date,id);
	}

	

	public List<ManageExpenseResponseDTO> getByPoultryExpenseId(UUID poultryId) {
				return manageExpenseDAO.getExpensePdf(poultryId);
	}
	public ManageExpense getByExpenseId(UUID id) {
		return manageExpenseDAO.findByexpenseId(id);
	}
	public List<ManageExpenseResponseDTO> getByPoultryExpenseId(String poultryName, String startDate, String endDate) {
		return manageExpenseDAO.getExpensePdf(poultryName,startDate,endDate);
	}
	public ManageExpenseResponseDTO getPdf(String poultryName, String startDate, String endDate) {
		return manageExpenseDAO.getPdf(poultryName, startDate, endDate);
	}
	public Object getBypoultryId() {
		// TODO Auto-generated method stub
		return manageExpenseDAO.getAllExpense();
	}
	

}
