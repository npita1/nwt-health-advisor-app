package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    ArticleEntity findById(long id);

    @Query("SELECT a FROM ArticleEntity a WHERE a.categoryEntity.name = :category")
    Iterable<ArticleEntity> findByCategory(String category);
}
