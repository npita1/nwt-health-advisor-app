package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="doctorInfo")
@Entity
public class DoctorInfoEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Polje za detalje o doktoru ne smije biti prazno.")
    @Size(min = 3, message = "Opis mora biti najmanje dzužine 20 znakova.")
    private String about;

    private String specialization;

    @Pattern(regexp = "^[\\p{L} -]+ - [\\p{L} -]+$", message = "Format dostupnosti mora biti 'Rijec - Rijec'.")
    private String availability;

    @Pattern(regexp = "^\\(\\d{3}\\)\\s*\\d{8}$", message = "Format broja telefona mora biti '(brojbrojbroj) brojbrojbroj'.")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Valid
    private UserEntity user;



    @Override
    public String toString() {
        return String.format(
                "DoctorInfoEntity[id=%d, about='%s', specialization ='%s', availability='%s', user='%s']",
                id, about, specialization, availability, user);
    }

    public String getAbout() {
        return about;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

