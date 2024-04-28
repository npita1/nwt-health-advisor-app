package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.exceptions.AppointmentNotFoundException;
import com.example.accessingdatamysql.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public AppointmentEntity Details(Long id) {
        Optional<AppointmentEntity> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent())
            return appointment.get();

        throw new AppointmentNotFoundException("Not found Notification by id  " + id);
    }

    @Override
    public AppointmentEntity Update(AppointmentEntity appointmentPatched) {
        return appointmentRepository.save(appointmentPatched);
    }

    @Override
    public Iterable<AppointmentEntity> ListAppointmentsByDoctorName(String doctorName) {
        Iterable<AppointmentEntity> appointments = appointmentRepository.findAppointmentsByDoctorName(doctorName);
        return appointments;
    }

    @Override
    public Iterable<AppointmentEntity> ListAppointmentsByUserName(String userName) {
        Iterable<AppointmentEntity> appoitnments=appointmentRepository.findAppointmentsByUserName(userName);
        return appoitnments;
    }

    @Override
    public Iterable<AppointmentEntity> ListAppointmentsByDescriptionAndUserName(String userName, String description) {
        Iterable<AppointmentEntity> appointments = appointmentRepository.findAppointmentsByUserAndDescription(userName,description);
        return appointments;
    }
}
