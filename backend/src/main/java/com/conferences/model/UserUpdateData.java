package com.conferences.model;

import com.conferences.annotation.Password;
import com.conferences.annotation.PasswordMatches;
import com.conferences.annotation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@PasswordMatches
public class UserUpdateData {

    @NotNull
    @NotEmpty
    @Size(min = 4)
    private String login;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String surname;

    private String oldPassword;

    @Password
    private String newPassword;

    @Password
    private String confirmPassword;
}
