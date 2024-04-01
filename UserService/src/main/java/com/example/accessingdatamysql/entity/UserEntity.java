package com.example.accessingdatamysql.entity;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Table(name="user")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Email ne smije biti prazan.")
    @Email(message="Email adresa nije validna")
    private String email;
    @NotBlank(message = "Polje za ime ne smije biti prazno.")
    @Size(min = 3, max = 20, message = "Ime mora biti dužine između 3 i 20 znakova.")
    private String firstName;

    @NotBlank(message = "Polje za prezime ne smije biti prazna.")
    @Size(min = 3, max = 30, message = "Lozinka mora biti dužine između 3 i 30 znakova.")
    private String lastName;

    private Integer type;

    @NotBlank(message = "Lozinka ne smije biti prazna.")
    @Size(min = 8, max = 20, message = "Lozinka mora biti dužine između 8 i 20 znakova.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "Lozinka mora sadržavati barem jedan broj, jedno veliko slovo, jedno malo slovo i jedan specijalni znak.")
    private String passwordHash;
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private DoctorInfoEntity doctorInfo;
    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity() {}

    public UserEntity(String email, String firstName, String lastName, Integer type, String passwordHash) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.passwordHash = passwordHash;
    }

    public UserEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getType() {
        return type;
    }

    public String getPasswordHash() {
        return passwordHash;
    }


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, email='%s', firstName='%s', lastName='%s', type=%d, passwordHash='%s']",
                id, email, firstName, lastName, type, passwordHash);
    }


}
