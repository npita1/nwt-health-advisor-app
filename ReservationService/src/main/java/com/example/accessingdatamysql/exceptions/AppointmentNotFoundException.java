package com.example.accessingdatamysql.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppointmentNotFoundException(Throwable cause) {
        super(cause);
    }
}
