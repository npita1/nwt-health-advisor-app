package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
