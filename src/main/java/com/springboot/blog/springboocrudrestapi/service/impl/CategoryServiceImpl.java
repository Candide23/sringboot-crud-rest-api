package com.springboot.blog.springboocrudrestapi.service.impl;

import com.springboot.blog.springboocrudrestapi.entity.Category;
import com.springboot.blog.springboocrudrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springboocrudrestapi.payload.CategoryDto;
import com.springboot.blog.springboocrudrestapi.repository.CategoryRepository;
import com.springboot.blog.springboocrudrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId) );

        return modelMapper.map(category, CategoryDto.class);
    }
}
