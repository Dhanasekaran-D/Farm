package com.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Customer;
@Repository
public interface CustomerRepository extends WriteableRepository<Customer, UUID> {
  
    Optional<Customer> findByName(String name);
    
    @Query("SELECT AI FROM Customer AI WHERE AI.name = :name and AI.id <> :excludeId")
    Optional<Customer> findByNameExcludeId(@Param("name") String name, @Param("excludeId") UUID excludeId);

	Customer findByPhoneNumber(String phoneNumber);

	
}
