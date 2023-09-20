package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.entity.ProductSales;
import com.app.enumeration.RequestType;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class SalesValidator {
	
	@Autowired
	MessagePropertyService messagePropertyService;
	
	public ValidationResult validateBill(RequestType put,ProductSales sale) {
		
List<String> errors = new ArrayList<String>();
		
		ValidationResult result = new ValidationResult();
		
		
		if (ValidationUtil.isNull(sale.getDiscount())) {
			errors.add(messagePropertyService.getMessage("sales.discount.required"));
		}
		if (ValidationUtil.isNull(sale.getTotalPayable())) {
			errors.add(messagePropertyService.getMessage("sales.totalpayable.required"));
		}
		
		if (errors.size() > 1) {
			String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
			throw new ObjectInvalidException(errorMessage);
		}
		result.setObject(sale);
		return result;
		
	}
}
