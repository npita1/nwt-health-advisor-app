package com.example.accessingdatamysql.rmq;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCreatedEvent {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;


}