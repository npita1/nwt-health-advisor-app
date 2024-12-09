package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name="doctorInfo")
@Entity
public class DoctorInfoEntity {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String about;
    private String specialization;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;



    @JsonIgnore
    @OneToMany(mappedBy = "doctorInfo")
    private List<EventEntity> events;
    public DoctorInfoEntity() {}

    @Override
    public String toString() {
        return String.format(
                "DoctorInfoEntity[id=%d, about='%s', user='%s']",
                id, about, user);
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
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }



    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

