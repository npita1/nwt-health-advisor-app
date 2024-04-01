package com.example.accessingdatamysql.entity;
import jakarta.persistence.*;

@Table(name="doctorInfo")
@Entity
public class DoctorInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String about;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public DoctorInfo() {}

    public DoctorInfo(User user, String references) {
        this.user = user;
        this.about = references;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String ref) {
        this.about = ref;
    }

    @Override
    public String toString() {
        return String.format(
                "DoctorInfo[id=%d, user='%s', references='%s']",
                id, user.toString(), about);
    }
}
