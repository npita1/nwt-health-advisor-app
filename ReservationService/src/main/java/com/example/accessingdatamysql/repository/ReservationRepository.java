package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity,Long> {
    ReservationEntity findById(long id);
    List<ReservationEntity> findByEvent(EventEntity event);
}
