package com.example.accessingdatamysql.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorRequest {

    @Email(message = "Email adresa nije validna")
    @NotBlank(message = "Email ne smije biti prazan.")
    private String email;

    @NotBlank(message = "Polje za ime ne smije biti prazno.")
    @Size(min = 3, max = 20, message = "Ime mora biti dužine između 3 i 20 znakova.")
    private String firstName;

    @NotBlank(message = "Polje za prezime ne smije biti prazna.")
    @Size(min = 3, max = 30, message = "Prezime mora biti dužine između 3 i 30 znakova.")
    private String lastName;

    @NotBlank(message = "Lozinka ne smije biti prazna.")
    private String password;

    @NotBlank(message = "Polje za detalje o doktoru ne smije biti prazno.")
    @Size(min = 3, message = "Opis mora biti najmanje dužine 20 znakova.")
    private String about;

    private String specialization;

    @Pattern(regexp = "^[\\p{L} -]+ - [\\p{L} -]+$", message = "Format dostupnosti mora biti 'Rijec - Rijec'.")
    private String availability;

    @Pattern(regexp = "^\\(\\d{3}\\)\\s*\\d{8}$", message = "Format broja telefona mora biti '(brojbrojbroj) brojbrojbroj'.")
    private String phoneNumber;
}