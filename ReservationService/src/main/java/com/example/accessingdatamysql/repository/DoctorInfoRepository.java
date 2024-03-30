package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfoEntity, Long> {
    DoctorInfoEntity findById(long id);
}
