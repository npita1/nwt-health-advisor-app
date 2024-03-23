package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.Category;
import com.example.accessingdatamysql.Entity.DoctorInfo;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
