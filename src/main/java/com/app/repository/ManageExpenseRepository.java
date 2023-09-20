package com.app.repository;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.ManageExpense;

@Repository
public interface ManageExpenseRepository extends WriteableRepository<ManageExpense, UUID> {


	Object findByExpenseHeadId(UUID expenseHeadId);

	ManageExpense findByExpenseHeadIdAndPoultryIdAndDate(UUID expenseHeadId, UUID poultryId, String date);

	
	 @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ManageExpense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.poultryId = :poultryId AND e.expenseHeadId =:expenseHeadId")
	   boolean existsByMonthAndYearAndPoultryId(@Param("month") int month, @Param("year") int year, @Param("poultryId") UUID poultryId,@Param("expenseHeadId") UUID expenseHeadId);
	 
	 @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ManageExpense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.poultryId = :poultryId AND e.expenseHeadId = :expenseHeadId AND e.id <> :id")
	 boolean existsByMonthAndYearAndPoultryIdExcludeId(@Param("month") int month, @Param("year") int year, @Param("poultryId") UUID poultryId, @Param("expenseHeadId") UUID expenseHeadId, @Param("id") UUID id);

}
