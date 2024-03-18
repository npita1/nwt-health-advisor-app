package com.example.accessingdatamysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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
}
