package com.example.accessingdatamysql.auth;

import com.example.accessingdatamysql.entity.Role;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 3, max = 20, message = "Ime mora biti dužine između 3 i 20 znakova.")
    private String firstname;
    @Size(min = 3, max = 30, message = "Prezime mora biti dužine između 3 i 30 znakova.")
    private String lastname;
    private String email;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "Lozinka mora sadržavati najmanje jedno malo slovo, jedno veliko slovo, jedan broj i jedan znak koji nije slovo ili broj."
    )
    private String password;
    private Role role;

    @AssertTrue(message = "Ime i prezime ne smiju biti isti.")
    public boolean isFirstNameNotLastName() {
        return !firstname.equalsIgnoreCase(lastname);
    }
}