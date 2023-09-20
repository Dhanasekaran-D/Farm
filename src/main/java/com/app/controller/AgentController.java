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

import com.app.dto.AgentDTO;
import com.app.entity.Agent;
import com.app.entity.ProductSales;
import com.app.entity.User;
import com.app.enumeration.RequestType;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.AgentService;
import com.app.service.MessagePropertyService;
import com.app.service.UserService;
import com.app.util.message.ResponseMessage;
import com.app.validator.AgentValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/agent")
@Api(value = "agent Rest API's", produces = "application/json")
public class AgentController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull AgentService agentService;
	private @NonNull AgentValidator agentValidator;
	private @NonNull UserService userService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create agent List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createAgent(@RequestBody AgentDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
	//	User user=new User();
		User userObj =userService.findByName(request.getAgentName());
		if (null != userObj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.user.duplicate"), HttpStatus.BAD_REQUEST);
		}
		User userPhoneObject =userService.findByPhone(request.getPhoneNo());
		if (null != userPhoneObject) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.usermobile.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			ValidationResult validationResult = agentValidator.validate(RequestType.POST, request);
			agentService.saveAgent((AgentDTO) validationResult.getObject());

			return responseGenerator.successResponse(context, messagePropertyService.getMessage("agent.create"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get agent List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAgent(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		Agent agent = new Agent();
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("agent.get"),
					agentService.getAll(agent), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get agent List.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAgent(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("agent.get"),
					agentService.getById(id), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get agent List.", response = Response.class)
	@RequestMapping(value = "/getby/{poultryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAgentById(@PathVariable("poultryId") UUID poultryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("agent.get"),
					agentService.getByPoultryId(poultryId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get agent List.", response = Response.class)
	@RequestMapping(value = "/getid/{districtId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAgentByDistrictId(@PathVariable("districtId") UUID districtId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("agent.get"),
					agentService.getByDistrictId(districtId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get agent List.", response = Response.class)
	@PutMapping(value = "/updateAgent", produces = "application/json")
	public ResponseEntity<?> updateAgent(@RequestBody Agent agent, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == agent.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Agent> agentObj = agentService.getid(agent.getId());
		if (null == agentObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Agent agentDuplicateName = agentService.getByAgentNameExcludeId(agent.getAgentName(), agent.getId());
		if (null != agentDuplicateName) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.name.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Agent agentDuplicate = agentService.findByNameExcludeId(agent.getPoultryId(), agent.getId());
		if (null != agentDuplicate) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.poultry.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Agent agentDuplicatePhone = agentService.getByPhoneNoExcludeId(agent.getPhoneNo(), agent.getId());
		if (null != agentDuplicatePhone) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.mobile.duplicate"), HttpStatus.BAD_REQUEST);
		}
		Agent agentDuplicatename = agentService.findByUserEmailExcludeId(agent. getEmailId(), agent.getId());
		if (null != agentDuplicatename) {
		
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("agent.email.duplicate"), HttpStatus.BAD_REQUEST);
		}
		agentService.saveOrUpdate(agent);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("agent.update"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to Delete agent List.", response = Response.class)
	@DeleteMapping(value = "/delete/{agentId}", produces = "application/json")
	public ResponseEntity<?> deleteAgent(@PathVariable("agentId") UUID agentId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("company updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		

		try {
			agentService.deleteById(agentId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("agent.delete"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while deleting agent {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("agent.invalid"), HttpStatus.BAD_REQUEST);
		}
	}

}
