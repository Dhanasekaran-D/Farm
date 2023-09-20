package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.dto.AgentDTO;
import com.app.entity.Agent;
import com.app.enumeration.RequestType;
import com.app.repository.RoleRepository;
import com.app.service.AgentService;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.NonNull;
@Service
public class AgentValidator {
	@Autowired
	MessagePropertyService messageSource;
	@Autowired
	private @NonNull AgentService agentService;
	@Autowired
	private @NonNull RoleRepository roleRepository;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	public ValidationResult validate(RequestType requestType, AgentDTO request) {

		errors = new ArrayList<>();
		ValidationResult result = new ValidationResult();
		Agent agent = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
			if (ValidationUtil.isNullOrEmpty(request.getAgentName())) {
				errors.add(messageSource.getMessage("agentname.required"));
			} else {
				request.setAgentName(ValidationUtil.getFormattedString(request.getAgentName()));
				if (!ValidationUtil.isValidName(request.getAgentName())) {
					errors.add(messageSource.getMessage("agentname.invalid"));
				}
			}
			Optional<Agent> userOptionalName = agentService.getByAgentName(request.getAgentName());
			if (userOptionalName.isPresent()) {
				String[] params = new String[] { request.getPhoneNo() };
				errors.add(messageSource.getMessage("agent.name.duplicate", params));
			}
			Optional<Agent> userOptional = agentService.getByPhoneNo(request.getPhoneNo());
			if (userOptional.isPresent()) {
				String[] params = new String[] { request.getPhoneNo() };
				errors.add(messageSource.getMessage("agent.mobile.duplicate", params));
			}
			Optional<Agent> userDuplicateMailObj = agentService.findByUserEmail(request.getEmailId());
			if (userDuplicateMailObj.isPresent()) {
				errors.add(messageSource.getMessage("agent.email.duplicate"));
			}
			Optional<Agent> userDuplicatepoultryId = agentService.findByAgentPoultryId(request.getPoultryId());
			if (userDuplicatepoultryId.isPresent()) {
				errors.add(messageSource.getMessage("agent.poultry.duplicate"));
			}
		} else {
			if (ValidationUtil.isNull(request.getId()))
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));

			Optional<Agent> userOptional = agentService.findById(request.getId());
			if (!userOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("agent.not.found"));
			}
			agent = userOptional.get();
		}
		if (ValidationUtil.isNullOrEmpty(request.getAgentName())) {
			errors.add(messageSource.getMessage("agentname.required"));
		} else {
			request.setAgentName(ValidationUtil.getFormattedString(request.getAgentName()));
			if (!ValidationUtil.isValidName(request.getAgentName())) {
				errors.add(messageSource.getMessage("agentname.invalid"));
			}
		}
		if (ValidationUtil.isNullOrEmpty(request.getEmailId())) {
			errors.add(messageSource.getMessage("agent.email.required"));
		} else {
			request.setEmailId(ValidationUtil.getFormattedString(request.getEmailId()));
			if (!ValidationUtil.isValidEmailId(request.getEmailId())) {
				errors.add(messageSource.getMessage("agent.email.invalid"));
			}
		}
		if (ValidationUtil.isValidPhoneNo(request.getPhoneNo())) {
			if (ValidationUtil.isNullOrEmpty(request.getPhoneNo())) {
				errors.add(messageSource.getMessage("agent.phone.no.required"));
			}
			errors.add(messageSource.getMessage("agent.phone.number.invalid"));
		} else {
			request.setPhoneNo(ValidationUtil.getFormattedString(request.getPhoneNo()));
			if (!ValidationUtil.isValidMobileNumber(request.getPhoneNo())) {
				errors.add(messageSource.getMessage("agent.phone.number.invalid"));
			}
		}
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		result.setObject(request);
		return result;

	}

}
