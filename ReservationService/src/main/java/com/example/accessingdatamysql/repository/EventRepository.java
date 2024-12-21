
package com.example.accessingdatamysql.repository;

import java.util.List;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import com.example.accessingdatamysql.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    EventEntity findById(long id);

    Iterable<EventEntity> findAllByDoctorInfo(DoctorInfoEntity doctorInfo);

    void deleteAllByDoctorInfo(DoctorInfoEntity doctorInfo);
}

