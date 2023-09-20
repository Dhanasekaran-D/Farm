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

import com.app.entity.ExpenseHead;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.ExpenseHeadService;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/expense")
@Api(value = "Expense Rest API's", produces = "application/json", consumes = "application/json")
public class ExpenseHeadController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull ExpenseHeadService expenseHeadService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create expense List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createExpense(@RequestBody ExpenseHead expense, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("expense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		if (null == expense) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		ExpenseHead expenseobj = expenseHeadService.findByName(expense.getExpenseType());
		if (null != expenseobj) {
			//String[] params = new String[] { expense.getExpenseType() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("expense.duplicate"/* , params */), HttpStatus.BAD_REQUEST);
		}

		try {
			expenseHeadService.save(expense);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("expense.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating expense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get expense List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getExpense(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("expense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("expense.get"),
					expenseHeadService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating expense {}", e);

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get expense List.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getExpenseById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("expense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("expense.get"),
					expenseHeadService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating expense {}", e);

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " update expense List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateExpense(@RequestBody ExpenseHead expense, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("expense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == expense.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<ExpenseHead> expenseObj = expenseHeadService.get(expense.getId());
		if (null == expenseObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		ExpenseHead expenseDuplicate = expenseHeadService.findByNameExcludeId(expense.getExpenseType(), expense.getId());
		if (null != expenseDuplicate) {
			String[] params = new String[] { expense.getExpenseType() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("expense.duplicate", params), HttpStatus.BAD_REQUEST);
		}
		expenseHeadService.saveOrUpdate(expense);

		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("expense.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating expense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " delete expense List.", response = Response.class)
	@DeleteMapping(value = "/delete/{expenseHeadId}", produces = "application/json")
	public ResponseEntity<?> deleteExpenseById(@PathVariable("expenseHeadId") UUID expenseHeadId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("expense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
			expenseHeadService.deleteById(expenseHeadId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("expense.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating expense {}", e);

			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("expense.invalid"), HttpStatus.BAD_REQUEST);
		}
	}

}