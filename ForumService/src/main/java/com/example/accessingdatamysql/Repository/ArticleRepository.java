package com.example.accessingdatamysql.Repository;

import com.example.accessingdatamysql.Entity.Article;
import com.example.accessingdatamysql.Entity.DoctorInfo;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
