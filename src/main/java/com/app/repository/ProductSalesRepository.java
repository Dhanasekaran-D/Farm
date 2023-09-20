package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.ProductSales;

@Repository
public interface ProductSalesRepository extends WriteableRepository<ProductSales, UUID> {

	ProductSales findBySalesNo(String salesNo);


}
