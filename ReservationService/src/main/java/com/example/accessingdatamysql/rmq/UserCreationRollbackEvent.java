package com.example.accessingdatamysql.rmq;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCreationRollbackEvent {
    private Long id;
    private String email;
}
