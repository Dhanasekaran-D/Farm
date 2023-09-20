package com.app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ExpenseHeadDAO;
import com.app.entity.ExpenseHead;
import com.app.repository.ExpenseHeadRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })

public class ExpenseHeadService {

	private @NonNull ExpenseHeadRepository expenseHeadRepository;
	
	@Autowired
	 ExpenseHeadDAO expenseDAO;

	public void save(ExpenseHead expense) {
		expenseHeadRepository.save(expense);

	}

	public Object getById(UUID id) {
		return expenseDAO.findById(id);
	}

	public void saveOrUpdate(ExpenseHead expense) {
		expenseHeadRepository.saveAndFlush(expense);

	}

	public void deleteById(UUID expenseHeadId) {
		expenseHeadRepository.deleteById(expenseHeadId);
		

	}

	public ExpenseHead findByName(String expenseType) {
		return expenseHeadRepository.findByExpenseType(expenseType);
	}

	public Object getAll() {
		return expenseDAO.findAll();
	}

	public Optional<ExpenseHead> get(UUID id) {
		return expenseHeadRepository.findById(id);
	}

	public ExpenseHead findByNameExcludeId(String expenseType, UUID id) {
		return expenseDAO.findByNameExcludeId(expenseType,id);
	}

}
