package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.DoctorInfo;
import org.springframework.data.repository.CrudRepository;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfo, Long> {
}
