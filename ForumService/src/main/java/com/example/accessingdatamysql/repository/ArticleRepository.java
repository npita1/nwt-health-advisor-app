package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    ArticleEntity findById(long id);
    void deleteById(Long aLong);

    @Query("SELECT a FROM ArticleEntity a WHERE a.category.name = :category")
    Iterable<ArticleEntity> findByCategory(String category);

    @Query("SELECT a FROM ArticleEntity a WHERE a.doctor.id = :doctorId")
    Iterable<ArticleEntity> getArticlesByDoctorId(long doctorId);

}
