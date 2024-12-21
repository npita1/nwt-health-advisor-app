package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.ArticleEntity;
import com.example.accessingdatamysql.entity.DoctorInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    ArticleEntity findById(long id);
    void deleteAllByDoctor(DoctorInfoEntity doctorInfo);

    @Query("SELECT a FROM ArticleEntity a WHERE a.category.name = :category")
    Iterable<ArticleEntity> findByCategory(String category);

    @Query("SELECT a FROM ArticleEntity a WHERE a.doctor.id = :doctorId")
    Iterable<ArticleEntity> getArticlesByDoctorId(long doctorId);

}
