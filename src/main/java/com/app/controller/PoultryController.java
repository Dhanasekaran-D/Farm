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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.PoultryEntity;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.PoultryService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/poultry")
@Api(value = "Poultry Rest API's", produces = "application/json", consumes = "application/json")
public class PoultryController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull PoultryService poultryService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create poultry List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createPoultry(@RequestBody PoultryEntity poultry, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("poultry created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		if (null == poultry) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		PoultryEntity poultryObj = poultryService.findByName(poultry.getPoultryName());
		if (null != poultryObj) {
			String[] params = new String[] { poultry.getPoultryName() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("poultry.duplicate", params), HttpStatus.BAD_REQUEST);
		}
		PoultryEntity poultryObject = poultryService.findByPoultryAddress(poultry.getAddress());
		if (null != poultryObject) {
			String[] param = new String[] { poultry.getAddress() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("poultry.duplicate", param), HttpStatus.BAD_REQUEST);
		}

		poultryService.savePoultry(poultry);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultry.create"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get poultry List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPoultry(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("poultry get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {

			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultry.get"),
					poultryService.getAll(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get poultry List.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("poultry get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultry.get"),
					poultryService.getById(id), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get poultry List.", response = Response.class)
	@RequestMapping(value = "/getby/{districtId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByDistrictId(@PathVariable("districtId") UUID districtId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("poultry get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultry.get"),
					poultryService.getDistrictById(districtId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to  Update poultry List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")

	public ResponseEntity<?> updatePoultry(@RequestBody PoultryEntity poultry, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("poultry updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		PoultryEntity poultryDuplicate = poultryService.findByNameExcludeId(poultry.getPoultryName(), poultry.getId());
		if (null != poultryDuplicate) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("poultry.duplicate"), HttpStatus.BAD_REQUEST);
		}
		PoultryEntity poultryAddressDuplicate = poultryService.findByAddressExcludeId(poultry.getAddress(), poultry.getId());
		if (null != poultryAddressDuplicate) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("poultry.duplicate"), HttpStatus.BAD_REQUEST);
		}
		poultryService.saveOrUpdate(poultry);

		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultry.update"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while updating poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to Delete poultry List.", response = Response.class)
	@DeleteMapping(value = "/delete/{poultryId}", produces = "application/json")
	public ResponseEntity<?> deletePoultry(@PathVariable("poultryId") UUID poultryId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("poultry deleted started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			poultryService.deletePoultryId(poultryId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultry.delete"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while deleting poultry {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("poultry.invalid"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all poultry details as a list.", response = Response.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchPoultry(@RequestParam("key") String key, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("poultry search started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultry.get"),
					poultryService.getAllPoultry(key), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
