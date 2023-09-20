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

import com.app.entity.Breed;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.BreedService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/breed")
@Api(value = "Breed Rest API's", produces = "application/json", consumes = "application/json")
public class BreedController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull BreedService breedService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create breed List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createBreed(@RequestBody Breed breed, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breed create started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == breed) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Breed breedobj = breedService.findByName(breed.getBreedName(),breed.getBreedTypeId());
		if (null != breedobj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("breed.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			breedService.saveBreed(breed);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breed.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while creating breed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to  Update breed List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateBreed(@RequestBody Breed breed, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breed updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == breed.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<Breed> breedObj = breedService.get(breed.getId());
		if (null == breedObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Breed breedDuplicate = breedService.findByNameExcludeId(breed.getBreedName(),breed.getBreedTypeId(), breed.getId());
		if (null != breedDuplicate) {
			String[] params = new String[] { breed.getBreedName() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("breed.duplicate", params), HttpStatus.BAD_REQUEST);
		}
		breedService.saveOrUpdate(breed);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breed.update"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting breed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to Delete breed List.", response = Response.class)
	@DeleteMapping(value = "/delete/{breedId}", produces = "application/json")
	public ResponseEntity<?> deleteBreed(@PathVariable("breedId") UUID breedId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("breed delete started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		
		try {
			breedService.deleteBreed(breedId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("breed.delete"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while deleting breed {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("breed.invalid"), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get breed List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getBreed(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("breed get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("breed.get"),
					breedService.getAll(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting breed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}



	@ApiOperation(value = " Get breed List By id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByIdBreed(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("breed get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("breed.get"),
					breedService.getId(id), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting breed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
