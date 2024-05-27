package com.example.accessingdatamysql.entity;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Table(name="user")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    //@NotBlank(message="Email ne smije biti prazan.")
    @Email(message="Email adresa nije validna")
    private String email;

    @NotBlank(message = "Polje za ime ne smije biti prazno.")
    @Size(min = 3, max = 20, message = "Ime mora biti dužine između 3 i 20 znakova.")
    private String firstName;

    @NotBlank(message = "Polje za prezime ne smije biti prazna.")
    @Size(min = 3, max = 30, message = "Prezime mora biti dužine između 3 i 30 znakova.")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "Lozinka ne smije biti prazna.")
    private String password;

    private Long userServiceId;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private DoctorInfoEntity doctorInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity() {}

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"last_name\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"role\":\"" + (role != null ? role.name() : "") + "\"," +
                "\"doctorInfo\":\"" + (doctorInfo != null ? doctorInfo.getId() : 0) + "\"" +
                "}";
    }
    @Override
    public String getPassword() {
        return password;
    }

    public Long getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(Long userServiceId) {
        this.userServiceId = userServiceId;
    }

    public Long getId() {
        return id;
    }
}
