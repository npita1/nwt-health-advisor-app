package com.example.accessingdatamysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name="doctorInfo")
@Entity
public class DoctorInfoEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String about;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;



    @OneToMany(mappedBy = "doctorInfo")
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "doctorInfo")
    private List<EventEntity> events;
    protected DoctorInfoEntity() {}

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
}

