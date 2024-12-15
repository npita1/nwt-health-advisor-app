package com.example.accessingdatamysql.auth;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    private String currentPassword;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Lozinka mora sadržavati najmanje jedno malo slovo, jedno veliko slovo, jedan broj i jedan znak koji nije slovo ili broj."
    )
    private String newPassword;
    private String confirmationPassword;
}

