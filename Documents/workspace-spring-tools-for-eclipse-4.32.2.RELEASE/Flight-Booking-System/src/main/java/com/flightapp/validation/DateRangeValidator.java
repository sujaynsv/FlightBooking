package com.flightapp.validation;

import com.flightapp.dto.InventoryRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, InventoryRequest> {
    
    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(InventoryRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }
        
        if (request.getDepartureDateTime() == null || request.getArrivalDateTime() == null) {
            return true; 
        }
        
        return request.getArrivalDateTime().isAfter(request.getDepartureDateTime());
    }
}
