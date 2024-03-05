package com.springboot.blog.springboocrudrestapi.repository;

import com.springboot.blog.springboocrudrestapi.entity.Category;
import com.springboot.blog.springboocrudrestapi.entity.Comment;
import com.springboot.blog.springboocrudrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
