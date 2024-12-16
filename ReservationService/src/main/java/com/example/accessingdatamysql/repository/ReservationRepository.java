package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.entity.ReservationEntity;
import com.example.accessingdatamysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    ReservationEntity findById(long id);
    List<ReservationEntity> findByEvent(EventEntity event);
    List<ReservationEntity> findByUserId(Integer userId);


    Optional<ReservationEntity> findByEventIdAndUser(Long eventId, UserEntity user);

}
