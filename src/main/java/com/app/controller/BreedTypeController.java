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

import com.app.entity.BreedType;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.BreedTypeService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/breedtype")
@Api(value = "BreedType Rest API's", produces = "application/json", consumes = "application/json")
public class BreedTypeController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull BreedTypeService breedTypeService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create breedType List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createBreed(@RequestBody BreedType breedType, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breedType create started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == breedType) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		BreedType breedTypeobj = breedTypeService.findByName(breedType.getBreedType());
		if (null != breedTypeobj) {
			String[] params = new String[] { breedType.getBreedType() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("breedType.duplicate",params), HttpStatus.BAD_REQUEST);
		}
		try {
			breedTypeService.save(breedType);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breedType.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while creating breedtype {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get breedType List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getBreedType(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("breedType get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("breedType.get"),
					breedTypeService.getAll(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting breedType {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	
	@ApiOperation(value = " Get breedType List.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getBreedTypeId(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breedType get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("breedType.get"),
					breedTypeService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while  getting breedType {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Update breedType List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateBreed(@RequestBody BreedType breedType, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breedType updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == breedType.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<BreedType> agentObj = breedTypeService.getid(breedType.getId());
		if (null == agentObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		BreedType agentDuplicate = breedTypeService.findByNameExcludeId(breedType.getBreedType(), breedType.getId());
		if (null != agentDuplicate) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("breedType.duplicate"), HttpStatus.BAD_REQUEST);
		}
		breedTypeService.saveOrUpdate(breedType);

		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breedType.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while creating breedtype {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " allow to delete breedType List.", response = Response.class)
	@DeleteMapping(value = "/delete/{breedTypeId}", produces = "application/json")
	public ResponseEntity<?> deleteById(@PathVariable("breedTypeId") UUID breedTypeId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breedType get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			breedTypeService.deleteById(breedTypeId);
		
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breedType.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while  getting breedType {}", e);
			return responseGenerator.errorResponse(context,  messagePropertyService.getMessage("breedType.invalid"), HttpStatus.BAD_REQUEST);
		}

	}
}
