package com.app.repository;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Transaction;
@Repository
public interface TransactionRepository extends WriteableRepository<Transaction,UUID>{

	//Transaction findByProductIdAndSaleDateAndPoultryId(UUID productId, Date salesDate, UUID poultryId);

	//Transaction findByProductIdAndTransactionDateAndPoultryId(UUID productId, Date transactionDate, UUID poultryId);

	Transaction findTotalByProductIdAndPoultryId(UUID productId, UUID poultryId);

	Transaction findByProductIdAndTransactionDate(UUID productId, Date transactionDate);

	Transaction findByProductId(UUID productId);

}
