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

import com.app.dto.ProductionDTO;
import com.app.entity.Production;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.ProductionService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/production")
@Api(value = "Production Rest API's", produces = "application/json", consumes = "application/json")
public class ProductionController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	@Autowired
	private @NonNull ProductionService productionService;

	
	@Autowired
	MessagePropertyService messagePropertyService;
	@ApiOperation(value = " Create production List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createProduct(@RequestBody ProductionDTO productiondto , @RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("production updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == productiondto) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Production production=new Production(); 
		Production productionObj = productionService.findByBreedIdAndPoultryIdAndCategoryIdAndDate(productiondto.getBreedId(),productiondto.getPoultryId(),productiondto.getCategoryId(),productiondto.getDate());
		if (null != productionObj) {
			//String[] params = new String[] { manage.getExpenseHeadId() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("production.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			productionService.save(productiondto);
			return responseGenerator.successResponse(context,messagePropertyService.getMessage("production.create"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating production {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

}
	@ApiOperation(value = " Get production List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProduction(@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("production updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context,messagePropertyService.getMessage("production.get"),productionService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating production {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch production by  id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("production updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			 
			
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("production.get"),
					productionService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating production {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to  Update production List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody Production production, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("production updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == production) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		//Production production=new Production(); 
		Production productionObj = productionService.findByBreedIdAndPoultryIdAndCategoryIdAndDateExcludeId(production.getBreedId(),production.getPoultryId(),production.getCategoryId(),production.getDate(),production.getId());
		if (null != productionObj) {
			//String[] params = new String[] { manage.getExpenseHeadId() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("production.duplicate"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			productionService.update(production);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("production.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating production {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete production List.", response = Response.class)
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("production delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		productionService.delete(id);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("production.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating production {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
	
