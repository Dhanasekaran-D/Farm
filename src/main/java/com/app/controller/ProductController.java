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

import com.app.entity.Product;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.PoultryService;
import com.app.service.ProductService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/product")
@Api(value = "Product Rest API's", produces = "application/json", consumes = "application/json")
public class ProductController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	@Autowired
	MessagePropertyService messagePropertyService;

	@Autowired
	private @NonNull ProductService productService;
	
	@Autowired
	private @NonNull PoultryService poultryService;

	@ApiOperation(value = " Create product List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createProduct(@RequestBody Product product, @RequestHeader HttpHeaders httpHeader)throws Exception {
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == product) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}	
		Product productobj = productService.findByName(product.getBreedId(),product.getCategoryId(),product.getPoultryId());
		if (null != productobj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("product.duplicate"), HttpStatus.BAD_REQUEST);
		}

		try {
			productService.save(product);

			return responseGenerator.successResponse(context, messagePropertyService.getMessage("product.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get product List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProduct(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("product.get"),
					productService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get product By id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)throws Exception {
		
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("product.get.one"),
					productService.get(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = " Get product By id.", response = Response.class)
	@RequestMapping(value = "/breed/get/{poultryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByPoultryId(@PathVariable("poultryId") UUID poultryId, @RequestHeader HttpHeaders httpHeader)throws Exception {
		
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("product.get.one"),
					productService.getpoultryId(poultryId), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to  Update product List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateCustomer(@RequestBody Product product, @RequestHeader HttpHeaders httpHeader)throws Exception {
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == product.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<Product> productObj = productService.getid(product.getId());
		if (null == productObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Product productDuplicate = productService.findByNameExcludeId(product.getBreedId(),product.getCategoryId(),product.getPoultryId(), product.getId());
		if (null != productDuplicate) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("product.duplicate"), HttpStatus.BAD_REQUEST);
		}
		productService.update(product);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("product.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	@ApiOperation(value = "Allow to Delete product List.", response = Response.class)
	@DeleteMapping(value = "/delete/{productId}", produces = "application/json")
	public ResponseEntity<?> deleteCustomer(@PathVariable("productId") UUID productId, @RequestHeader HttpHeaders httpHeader)throws Exception {
		logger.info("product delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			productService.delete(productId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("product.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("product.invalid"), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = " Get product List.", response = Response.class)
	@RequestMapping(value = "/get/poultry/product", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPoultryProduct(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		//List<ProductDTO> poultryBreed = productService.getProducts();
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("product.get"),
					productService.getProduct(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
		
		
	}
	@ApiOperation(value = " Get product List.", response = Response.class)
	@RequestMapping(value = "/get/poultry/sale/product", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPoultryProductQuantity(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("product updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		//List<ProductDTO> poultryBreed = productService.getProducts();
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("product.get"),
					productService.getProductQuantity(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating product {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
		
		
	}
}