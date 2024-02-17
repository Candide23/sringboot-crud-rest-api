package com.springboot.blog.springboocrudrestapi.repository;

import com.springboot.blog.springboocrudrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUserName(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}