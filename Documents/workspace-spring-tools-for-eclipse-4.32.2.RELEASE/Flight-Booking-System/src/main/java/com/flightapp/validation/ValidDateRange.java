package com.flightapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {
    
    String message() default "Arrival date must be after departure date";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
