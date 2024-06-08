package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name="user",uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
public class UserEntity {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Email
    @NotBlank(message="Email ne smije biti prazan.")
    @Column(nullable = false, unique = true)
    private String email;
    private String firstName;
    private String lastName;

    private Integer type;

    @NotBlank(message = "Lozinka ne smije biti prazna.")
    @Size(min = 0, max = 1000, message = "Lozinka mora biti dužine između 8 i 20 znakova.")
    private String password;
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private DoctorInfoEntity doctorInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AppointmentEntity> appointments;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ReservationEntity> reservations;
    public void setId(Long id) {
        this.id = id;
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



    public UserEntity() {}

    public UserEntity(String email, String firstName, String lastName, Integer type, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.password = password;
    }

    public UserEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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




    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, email='%s', firstName='%s', lastName='%s', type=%d, passwordHash='%s']",
                id, email, firstName, lastName, type, password);
    }


    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public DoctorInfoEntity getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfoEntity doctorInfo) {
        this.doctorInfo = doctorInfo;
    }


    public String getEmail() {
        return email;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
