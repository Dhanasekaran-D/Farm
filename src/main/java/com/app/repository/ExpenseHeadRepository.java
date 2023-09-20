package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.ExpenseHead;

@Repository
public interface ExpenseHeadRepository extends WriteableRepository<ExpenseHead, UUID>{

	ExpenseHead findByExpenseType(String expenseType);

}
