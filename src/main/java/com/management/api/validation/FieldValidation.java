package com.management.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {


    /// email validation
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr)  {

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();

    }


    /// phone_number validation
    public static final Pattern validPhoneNumber =   Pattern.compile("^( +254|0)([7][0-9]|[1][0-1])[0-9]{1,10}[0-9]{6}(\\d{5})$", Pattern.CASE_INSENSITIVE);

    public static boolean validatePhoneNumber(String phone_number)  {


           Matcher match = validPhoneNumber.matcher(phone_number);
           return match.find();


    }
}
