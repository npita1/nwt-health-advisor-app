package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.AppointmentEntity;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {
             AppointmentEntity findById(long id);
             List<AppointmentEntity> findByUser(UserEntity user);
             List<AppointmentEntity> findByDoctorInfo(DoctorInfoEntity doctorInfo);
    @Query("SELECT a FROM AppointmentEntity a WHERE a.doctorInfo.user.firstName = :doctorName")
    Iterable<AppointmentEntity> findAppointmentsByDoctorName( String doctorName);
    @Query("SELECT a FROM AppointmentEntity a WHERE a.user.firstName = :userName AND a.description = :description")
    Iterable<AppointmentEntity> findAppointmentsByUserAndDescription( String userName,  String description);
    @Query("SELECT a FROM AppointmentEntity a WHERE a.user.firstName = :userName")
    Iterable<AppointmentEntity> findAppointmentsByUserName( String userName);
}
