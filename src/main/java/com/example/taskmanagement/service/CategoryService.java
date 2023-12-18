package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.Category;
import com.example.taskmanagement.dao.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getByName(String name){
        return categoryRepository.getByName(name);
    }

    public Category add(Category category){
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    public Category changeName(Long id, String name){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Category not found. Id: %d", id)));
        category.setName(name);
        return categoryRepository.save(category);
    }
}
