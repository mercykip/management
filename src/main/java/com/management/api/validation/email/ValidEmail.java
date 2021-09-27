package com.management.api.validation.email;

import com.management.api.validation.phone.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailValidator.class)
public @interface  ValidEmail {
    public String message() default "Please provide valid Email";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
