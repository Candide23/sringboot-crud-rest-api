package com.springboot.blog.springboocrudrestapi.repository;

import com.springboot.blog.springboocrudrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
