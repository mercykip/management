package com.management.api.module;

import com.management.api.domain.Role;
import lombok.Getter;
import lombok.Setter;



import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




@Getter
@Setter
public class UserModel {

    private Long id;

    @Size(min=2, max=10, message = "first_name should have at least 2 characters")
    private String first_name;

    @Size(min=2, max=10,  message = "last_name should have at least 2 characters")
    private String last_name;

    @Size(min=2, max=10,  message = "other_name should have at least 2 characters")
    private String other_name;

    @Size(min=10, max=15,  message = "phone_number should have at least 2 characters")
    private String phone_number;

    @Email(message = "Username is invalid")
    private String username;

    @Size(min=4, message = "password should have at least 8 characters")

    private String password;

    /// load all roles whenever the user is fetched


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


}
