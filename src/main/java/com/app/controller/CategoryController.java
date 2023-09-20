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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Category;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.CategoryService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/category")
@Api(value = "Category Rest API's", produces = "application/json", consumes = "application/json")
public class CategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	@Autowired
	MessagePropertyService messagePropertyService;
	
	 private @NonNull CategoryService categoryService;
	
	
	
	@ApiOperation(value = " Create category List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createCategory(@RequestBody Category category, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("category create started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == category) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Category categoryObj = categoryService.findByName(category.getCategory());
		if (null != categoryObj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("category.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			categoryService.saveCategory(category);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("category.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while creating category {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
		@ApiOperation(value = " Get category List.", response = Response.class)
		@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<?> getProduct(@RequestHeader HttpHeaders httpHeader) throws Exception {
			logger.info("category updated started {}", LocalDateTime.now());
			TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

			try {
				return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("category.get"),
						categoryService.getAll(), HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error occured while updating category {}", e);
				return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
				
		@ApiOperation(value = " Get category List by Id.", response = Response.class)
		@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<?> getCategoryById(@PathVariable("id")  UUID id,@RequestHeader HttpHeaders httpHeader) throws Exception {
			logger.info("category updated started {}", LocalDateTime.now());
			TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

			try {
				return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("category.get"),
						categoryService.getById(id), HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error occured while updating category {}", e);
				return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	
	}
		@ApiOperation(value = "Update category List.", response = Response.class)
		@PutMapping(value = "/update", produces = "application/json")
		public ResponseEntity<?> updateCategory(@RequestBody Category category, @RequestHeader HttpHeaders httpHeader)
				throws Exception {
			logger.info("Category create started {}", LocalDateTime.now());

			TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
			if (null == category.getId()) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
						HttpStatus.BAD_REQUEST);
			}
			Optional<Category> categoryObj = categoryService.get(category.getId());
			if (null == categoryObj) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
						HttpStatus.BAD_REQUEST);
			}
			Category categoryDuplicate = categoryService.findByNameExcludeId(category.getCategory(), category.getId());
			if (null != categoryDuplicate) {
				String[] params = new String[] { category.getCategory() };
				return responseGenerator.errorResponse(context,
						messagePropertyService.getMessage("category.duplicate", params), HttpStatus.BAD_REQUEST);
			}
			
			try {
				categoryService.saveorUpdate(category);
				return responseGenerator.successResponse(context, messagePropertyService.getMessage("category.update"),
						HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error occured while creating category {}", e);
				return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		
		@ApiOperation(value = " Delete category List by Id.", response = Response.class)
		@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
		public ResponseEntity<?> deleteCategoryById(@PathVariable("id")  UUID id,@RequestHeader HttpHeaders httpHeader) throws Exception {
			logger.info("category updated started {}", LocalDateTime.now());
			TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

			try {
				categoryService.deleteById(id);
				return responseGenerator.successResponse(context, messagePropertyService.getMessage("category.delete"),
						 HttpStatus.OK);
			} catch (Exception e) {
				logger.error("error occured while updating category {}", e);
				return responseGenerator.errorResponse(context,  messagePropertyService.getMessage("category.invalid"), HttpStatus.BAD_REQUEST);
			}
		}
	}
	

