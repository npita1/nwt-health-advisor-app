package com.example.accessingdatamysql.exceptions;

public class DoctorInfoNotFoundException extends RuntimeException{
    public DoctorInfoNotFoundException(String message) {
        super(message);
    }

    public DoctorInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoctorInfoNotFoundException(Throwable cause) {
        super(cause);
    }
}
