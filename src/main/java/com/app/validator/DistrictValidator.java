package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.dto.DistrictDTO;
import com.app.entity.DistrictEntity;
import com.app.enumeration.RequestType;
import com.app.service.CountryService;
import com.app.service.DistrictService;
import com.app.service.MessagePropertyService;
import com.app.service.StateService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class DistrictValidator {
	
	@Autowired
	MessagePropertyService messageSource;

	private @NonNull DistrictService districtService;

	private @NonNull StateService stateService;

	private @NonNull CountryService countryService;

	List<String> errors = null;
	List<String> errorsObj = null;
	Optional<Subject> subject = null;

	/**
	 * method for UnitOfMeasurement validation Added by Ulaganathan
	 * 
	 * @param httpHeader
	 * @return ValidationResult
	 * @throws Exception
	 */
	public ValidationResult validate(RequestType requestType, DistrictDTO request) {

		errors = new ArrayList<>();
		DistrictEntity district = null;

		if (requestType.equals(RequestType.POST)) {
			if (!ValidationUtil.isNull(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}
			Optional<DistrictEntity> DistrictDuplicateObj = districtService.findByName(request.getDistrictName());
			if (DistrictDuplicateObj.isPresent()) {
				String[] params = new String[] { request.getDistrictName() };
				errors.add(messageSource.getMessage("district.name.duplicate", params));
			}
		} else {
			if (ValidationUtil.isNull(request.getId())) {
				throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
			}

			Optional<DistrictEntity> districtOptional = districtService.findById(request.getId());
			if (!districtOptional.isPresent()) {
				throw new ObjectInvalidException(messageSource.getMessage("district.not.found"));
			}

			district = districtOptional.get();
		}

		if (ValidationUtil.isNullOrEmpty(request.getDistrictName())) {
			errors.add(messageSource.getMessage("district.name"));
		} else {
			request.setDistrictName(ValidationUtil.getFormattedString(request.getDistrictName()));
			if (!ValidationUtil.isValidName(request.getDistrictName())) {
				errors.add(messageSource.getMessage("district.name.invalid"));
			}
		}

		if (ValidationUtil.isNullOrEmpty(request.getShortName())) {
			errors.add(messageSource.getMessage("district.short.name.required"));
		}



		ValidationResult result = new ValidationResult();
		if (errors.size() > 0) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}

		if (null == district) {
			district = DistrictEntity.builder().stateId(request.getStateId()).countryId(request.getCountryId())
					.districtName(request.getDistrictName()).shortName(request.getShortName())
					.build();
		} else {

			district.setShortName(request.getShortName());
			district.setDistrictName(request.getDistrictName());
			district.setStateId(request.getStateId());
			district.setCountryId(request.getCountryId());
		}
		result.setObject(district);
		return result;
	}
}
