package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.SaleNo;
@Repository
public interface SaleNoRepository extends WriteableRepository <SaleNo ,UUID> {

}

