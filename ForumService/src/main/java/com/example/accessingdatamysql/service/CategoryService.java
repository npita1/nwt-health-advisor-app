package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.CategoryEntity;
import com.example.accessingdatamysql.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity addCategory(CategoryEntity category) {
        return this.categoryRepository.save(category);
    }

    public Iterable<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(long id) {
        this.categoryRepository.deleteById(id);
    }

    public CategoryEntity getCategoryById(long id) {
        return this.categoryRepository.findById(id);
    }

}
