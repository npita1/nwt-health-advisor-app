package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.DoctorInfo;
import com.example.accessingdatamysql.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface DoctorInfoRepository extends CrudRepository<DoctorInfo, Long> {
}
