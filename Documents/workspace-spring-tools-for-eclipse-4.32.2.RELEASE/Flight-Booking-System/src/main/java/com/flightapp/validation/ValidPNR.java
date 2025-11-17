package com.flightapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PNRValidator.class)
@Documented
public @interface ValidPNR {
    
    String message() default "Invalid PNR format. Must be 10 alphanumeric characters";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
