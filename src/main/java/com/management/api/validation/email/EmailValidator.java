package com.management.api.validation.email;

import com.management.api.validation.phone.ValidPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator  implements ConstraintValidator<ValidEmail, Object> {
    public static final Pattern validEmail =   Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
        String email = value.toString().trim();
        return validateEmail(email);
        }
catch(Exception e){
            throw  new RuntimeException("Email Validation failed");

        }
    }

    public static boolean validateEmail(String email) {

            Matcher match = validEmail.matcher(email);
            return match.find();



    }
}
