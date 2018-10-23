package com.example.restapi.controller;

import com.example.restapi.exeptions.ResourceNotFoundException;
import com.example.restapi.model.Category;
import com.example.restapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class CategoryController {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public List<Category> get() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    public Category add(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryRequest) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryRequest.getName());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

}
