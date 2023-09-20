package com.app.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.repository.CustomerRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.CustomerService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/customer")
@Api(value = "Customer Rest API's", produces = "application/json", consumes = "application/json")
public class CustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull CustomerRepository customerRepository;

	private @NonNull CustomerService customerService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@Autowired
	MessagePropertyService messageSource;

	@ApiOperation(value = " Create customer List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createCoustomer(@RequestBody Customer customer, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == customer) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Customer customerobj = customerService.findByPhoneNo(customer.getPhoneNumber());
		if (null != customerobj) {
			//String[] params = new String[] { expense.getExpenseType() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("customer.phoneNumber.duplicate"/* , params */), HttpStatus.BAD_REQUEST);
		}
		try {
			customerService.save(customer);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("customer.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating customer {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allows to fetch all customer list.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("customer updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("customer.get.list"),
					customerService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating customer {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch customer by  id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			
			return responseGenerator.successGetResponse(context, messageSource.getMessage("customer.get.one"),
					customerService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating customer {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	@ApiOperation(value = "Allow to  Update customer List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == customer.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<Customer> customerObj = customerService.get(customer.getId());
		if (null == customerObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Customer customerDuplicate = customerService.findByNameExcludeId(customer.getPhoneNumber(), customer.getId());
		if (null != customerDuplicate) {
			String[] params = new String[] { customer.getPhoneNumber() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("customer.phoneNumber.duplicate", params), HttpStatus.BAD_REQUEST);
		}
		customerService.update(customer);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("customer.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating customer {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete customer List.", response = Response.class)
	@DeleteMapping(value = "/delete/{customerId}", produces = "application/json")
	public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") UUID customerId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			customerService.delete(customerId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("customer.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating customer {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("customer.invalid"), HttpStatus.BAD_REQUEST);
		}
	}
}
