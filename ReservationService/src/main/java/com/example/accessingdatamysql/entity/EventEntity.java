
package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Table(name="event")
@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Morate unijeti lokaciju događaja.")
    private String location;

    @Size(min = 5, max = 100, message = "Ime radionice/eventa mora biti dužine između 5 i 100 znakova.")
    private String name;

    @Size(min =10, max = 255, message = "Opis radionice/eventa mora biti dužine između 10 i 255 znakova.")
    private String description;

    //@PastOrPresent(message = "Datum mora biti u prošlosti ili današnji datum.")
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    @NotNull(message = "Liječnik mora biti dodijeljen događaju.")
    @Valid
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorInfoEntity doctorInfo;
    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<ReservationEntity> reservations;

    public EventEntity() {
    }

    @Override
    public String toString() {
        return String.format(
                "EventEntity[id=%d, name='%s', description='%s, date='%s, location='%s']",
                id, name, description, date, location);
    }

    public EventEntity(String name, String description, String date, String location) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
    }
    public EventEntity(String name, String description, String date, String location,DoctorInfoEntity doctor) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.doctorInfo=doctor;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public DoctorInfoEntity getDoctorInfo() {
        return doctorInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDoctorInfo(DoctorInfoEntity doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}


