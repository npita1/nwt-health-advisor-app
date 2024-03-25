
package com.example.accessingdatamysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="event")
@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String date;
    private String location;

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
                "DoctorInfoEntity[id=%d, name='%s', description='%s, date='%s, location='%s']",
                id, name, description, date, location);
    }

    public EventEntity(String name, String description, String date, String location) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
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
}


