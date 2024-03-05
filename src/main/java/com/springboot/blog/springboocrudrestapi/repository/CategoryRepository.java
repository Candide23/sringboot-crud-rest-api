package com.springboot.blog.springboocrudrestapi.repository;

import com.springboot.blog.springboocrudrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
