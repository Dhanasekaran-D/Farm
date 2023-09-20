package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CustomerDAO;
import com.app.dto.CustomerResponse;
import com.app.entity.Customer;
import com.app.repository.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CustomerService {
	private @NonNull CustomerRepository customerRepository;
	private @NonNull CustomerDAO customerDAO;
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	public void update(Customer customer) {
		customerRepository.saveAndFlush(customer);

	}

	public void delete(UUID customerId) {
		customerRepository.deleteById(customerId);
		
	}

	public List<CustomerResponse> findAll() {
		return customerDAO.findAll();
	}

	public List<CustomerResponse> findById(UUID id) {
		return customerDAO.findById(id);
	}



	public Customer findByPhoneNo(String phoneNumber) {
		return customerRepository.findByPhoneNumber(phoneNumber);
	}

	public Optional<Customer> get(UUID id) {
		return customerRepository.findById(id);
	}

	public Customer findByNameExcludeId(String phoneNumber, UUID id) {
		return customerDAO.findByNameExcludeId(phoneNumber,id);
	}


}
