package com.springboot.blog.springboocrudrestapi.service;

import com.springboot.blog.springboocrudrestapi.payload.CategoryDto;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

}
