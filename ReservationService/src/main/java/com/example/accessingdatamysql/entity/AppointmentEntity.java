
package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Table(name="appointment")
@Entity
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorInfoEntity doctorInfo;
    public AppointmentEntity(String description) {
        this.description = description;
    }
    public AppointmentEntity() {
    }
    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    @Override
    public String toString() {
        return String.format(
                "AppointmentEntity[id=%d,  description='%s]",
                id, description);
    }

    public void setUser(UserEntity user) {
        this.user=user;
    }
    public  void setDoctorInfo(DoctorInfoEntity doctorInfo){
        this.doctorInfo=doctorInfo;
    }
    public UserEntity getUser(){
        return user;
    }
    public DoctorInfoEntity getDoctorInfo(){
        return  doctorInfo;
    }
}


