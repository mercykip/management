package com.management.api.module;

import com.management.api.validation.email.ValidEmail;
import com.management.api.validation.phone.ValidPhone;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserModel {

    private Long id;
    @NotBlank
    @Size(min=2, max=10, message = "first_name should have at least 2 characters")
    private String first_name;

    @NotBlank
    @Size(min=2, max=10,  message = "last_name should have at least 2 characters")
    private String last_name;

    @NotBlank
    @Size(min=2, max=10,  message = "other_name should have at least 2 characters")
    private String other_name;

//    @ValidPhone
    @NotBlank
    private String phone_number;

    @ValidEmail
    @NotBlank
    private String username;

    @Size(min=4, message = "password should have at least 8 characters")
    @NotBlank
    private String password;


}
