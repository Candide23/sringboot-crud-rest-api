package com.springboot.blog.springboocrudrestapi.controller;


import com.springboot.blog.springboocrudrestapi.payload.LoginDto;
import com.springboot.blog.springboocrudrestapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login Rest API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
