package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

        CategoryEntity findById (long id);
        void deleteById(long id);
}
