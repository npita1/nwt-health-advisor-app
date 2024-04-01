package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfoEntity, Long> {
    DoctorInfoEntity findById(long id);
}
