package com.management.api.validation.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, Object> {
    public static final Pattern validPhoneNumber =   Pattern.compile("^(?:254|\\+254|0)?(7[0-6][0-9]{1,7})$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            String phone = value.toString().trim();
            return validatePhoneNumber(phone);
        }
        catch(Exception e){
            throw  new RuntimeException("Phone number not Valid");

        }
    }


    public static boolean validatePhoneNumber(String phone_number) {
        Matcher match = validPhoneNumber.matcher(phone_number);
        return match.find();

    }


}
