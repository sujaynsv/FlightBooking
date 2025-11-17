package com.flightapp.validation;

import com.flightapp.util.PNRGenerator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PNRValidator implements ConstraintValidator<ValidPNR, String> {
    
    @Override
    public void initialize(ValidPNR constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(String pnr, ConstraintValidatorContext context) {
        if (pnr == null || pnr.isEmpty()) {
            return true; // Let @NotBlank handle empty validation
        }
        
        return PNRGenerator.isValidPNR(pnr);
    }
}
