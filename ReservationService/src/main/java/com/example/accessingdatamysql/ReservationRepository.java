package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity,Long> {
    ReservationEntity findById(long id);
}
