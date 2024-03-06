package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
    List<CategoryDto> getAllCategories();

    CategoryDto updateCategories(Long categoryId, CategoryDto categoryDto);

}
