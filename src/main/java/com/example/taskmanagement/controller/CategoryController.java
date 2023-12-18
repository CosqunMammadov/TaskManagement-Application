package com.example.taskmanagement.controller;

import com.example.taskmanagement.dao.entity.Category;
import com.example.taskmanagement.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/by-name/{name}")
    @Operation(summary = "This endpoint help us to get category by name")
    public Category getByName(@PathVariable String name){
        return categoryService.getByName(name);
    }

    @PostMapping("/add")
    @Operation(summary = "This endpoint help us to add new category")
    public Category add(@RequestBody Category category){
        return categoryService.add(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }

    @PatchMapping("/change-name/{id}")
    @Operation(summary = "This endpoint help us to change category name")
    public Category changeName(@PathVariable Long id, @RequestParam String name){
        return categoryService.changeName(id, name);
    }
}
