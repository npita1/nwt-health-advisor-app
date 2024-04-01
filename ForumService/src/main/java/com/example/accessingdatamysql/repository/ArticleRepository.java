package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findById(long id);
    Iterable<Article> findByCategory(String category);
}
