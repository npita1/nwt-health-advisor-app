package com.example.accessingdatamysql.controller;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.exceptions.CategoryNotFoundException;
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
@CrossOrigin
@RequestMapping(path="/forum")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = "/addCategory")
    public @ResponseBody  ResponseEntity<String> addCategory(@Valid @RequestBody CategoryEntity category) {
        CategoryEntity addedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok("Category added.");
    }

    @GetMapping(path="/allCategories")
    public @ResponseBody Iterable<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path="/category/{categoryId}")
    public @ResponseBody CategoryEntity getCategoory(@PathVariable long categoryId) {
        CategoryEntity category = categoryService.getCategoryById(categoryId);

        if (category == null) {
            throw new CategoryNotFoundException("Not found category by id: " + categoryId);
        }

        return category;
    }

    // Kaskadno brisanje vidi kad bude trebalo
    @DeleteMapping(path="/deleteCategory/{id}")
    public @ResponseBody ResponseEntity<String> deleteCategory (@PathVariable long id) {
        CategoryEntity category = this.categoryService.findById(id);
        if(category != null) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        throw new CategoryNotFoundException("Not found category by id: " + id);
    }



}