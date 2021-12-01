package com.example.househunt.category.rest;

import com.example.househunt.category.domain.Category;
import com.example.househunt.category.domain.CategoryDTO;
import com.example.househunt.category.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryResource {

    final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategoryFromDto(@RequestBody CategoryDTO categoryDTO) {
        var newCategory = categoryService.createNewCategoryAndSave(categoryDTO);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(id));
    }
}
