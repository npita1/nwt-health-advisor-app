package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.repository.*;

import com.example.accessingdatamysql.exceptions.ArticleNotFoundException;
import com.example.accessingdatamysql.exceptions.ForumQuestionNotFoundException;
import com.example.accessingdatamysql.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping(path="/nwt")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path="/addCategory")
    public @ResponseBody String addNewCategory (@Valid @RequestBody CategoryEntity category) {
        CategoryEntity newCategory = categoryService.addCategory(category);
        return "Category Saved";
    }

    @GetMapping(path="/allCategories")
    public @ResponseBody Iterable<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path="/category/{categoryId}")
    public @ResponseBody CategoryEntity getCategoory(@PathVariable long categoryId) {
        CategoryEntity category = categoryService.getCategoryById(categoryId);

        if (category == null) {
            throw new ArticleNotFoundException(" Not found articleEntity by id: " + categoryId);
        }

        return category;
    }

    @DeleteMapping(path="/deleteCategory/{id}")
    public void deleteCategory (@PathVariable long id) {
        categoryService.deleteCategory(id);
    }



}