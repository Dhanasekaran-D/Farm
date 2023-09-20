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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Role;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.RoleService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/role")
@Api(value = "Role Rest API's", produces = "application/json", consumes = "application/json")
public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	@Autowired
	private @NonNull RoleService roleService;
	
	@Autowired
	MessagePropertyService messagePropertyService;

	@ApiOperation(value = " Create role List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createRole(@RequestBody Role role, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("role updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == role) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Role roleobj = roleService.findByName(role.getRoleName());
		if (null != roleobj) {
			//String[] params = new String[] { breed.getBreedName() };
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("role.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			roleService.save(role);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("role.create"),
				 HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating role {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	@ApiOperation(value = " Get role List.", response = Response.class)
	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getRole(@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("role updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context,messagePropertyService.getMessage("role.get"),roleService.get(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating role {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = " Get role List.", response = Response.class)
	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ResponseEntity<?> getById(@PathVariable ("id") UUID id,@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("role updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context,messagePropertyService.getMessage("role.get"),roleService.getByid(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating role {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = " Get role List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody Role role ,@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("role updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == role.getId()) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		Optional<Role> roleObj = roleService.getid(role.getId());
		if (null == roleObj) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_OBJECT_REFERENCE,
					HttpStatus.BAD_REQUEST);
		}
		Role roleDuplicate = roleService.findByNameExcludeId(role.getRoleName(), role.getId());
		if (null != roleDuplicate) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("role.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			roleService.udate(role);
			return responseGenerator.successResponse(context,messagePropertyService.getMessage("role.update"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating role {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = " Get role List.", response = Response.class)
	@DeleteMapping(value = "/delete/{roleId}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable ("roleId") UUID roleId,@RequestHeader HttpHeaders httpHeader)  throws Exception {
		logger.info("role updated started {}",LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			roleService.delete(roleId);
			return responseGenerator.successResponse(context,messagePropertyService.getMessage("role.delete"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating role {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("role.invalid"), HttpStatus.BAD_REQUEST);
		}
	}
}
