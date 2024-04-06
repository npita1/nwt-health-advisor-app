package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.AppointmentEntity;

public interface AppointmentService {

    AppointmentEntity Details(Long id);
    AppointmentEntity Update(AppointmentEntity appointmentPatched);
    Iterable<AppointmentEntity> ListAppointmentsByDoctorName(String doctorName);
    Iterable<AppointmentEntity>ListAppointmentsByDescriptionAndUserName(String userName,  String description);
}
