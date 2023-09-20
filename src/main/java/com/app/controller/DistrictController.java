package com.app.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

import com.app.dto.DistrictDTO;
import com.app.entity.DistrictEntity;
import com.app.entity.PoultryEntity;
import com.app.enumeration.RequestType;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.DistrictService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;
import com.app.validator.DistrictValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("api/district")
@Api(value = "district Rest API's", produces = "application/json", consumes = "application/json")
public class DistrictController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private @NonNull DistrictService districService;
	
	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	@Autowired
	MessagePropertyService messagePropertyService;
	
	private @NonNull DistrictValidator districtValidator;

	@ApiOperation(value = "Allows to create new District.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The District request payload") @RequestBody DistrictDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		
		logger.info("district create started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			ValidationResult validationResult = districtValidator.validate(RequestType.POST, request);
			districService.saveOrUpdate((DistrictEntity) validationResult.getObject());

			return responseGenerator.successResponse(context, messagePropertyService.getMessage("district.create"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allows to fetch all district  list.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("district get started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("district.get.list"), districService.findAll(),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allow to  Update district List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateDistrict(@RequestBody  DistrictEntity request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("district updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == request) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		DistrictEntity districtObj = districService.findByDistrictName(request.getDistrictName(),request.getId());
		if (null != districtObj) {
			String[] params = new String[] { request.getDistrictName() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("district.name.duplicate", params), HttpStatus.BAD_REQUEST);
		}

		try {
			districService.Update(request);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("district.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allow to Delete district List.", response = Response.class)
	@DeleteMapping(value = "/delete/{districtId}", produces = "application/json")
	public ResponseEntity<?> deleteDistrict(@PathVariable("districtId") UUID districtId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("district delete started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			districService.deleteById(districtId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("district.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}",e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("district.invalid"), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allows to  get by districtid.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDistrictId(@PathVariable("id") UUID id, 
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("district get started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("district.get"), districService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating district {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
