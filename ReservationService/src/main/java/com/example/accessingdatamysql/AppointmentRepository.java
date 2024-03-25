package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface AppointmentRepository extends CrudRepository<AppointmentEntity,Long> {
             AppointmentEntity findById(long id);
             List<AppointmentEntity> findByUser(UserEntity user);
             List<AppointmentEntity> findByDoctorInfo(DoctorInfoEntity doctorInfo);
}
